package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.dto.AuthenticationFacade;
import com.mvp.mvp_translation_project.exceptions.InvalidDataException;
import com.mvp.mvp_translation_project.exceptions.InvalidPasswordException;
import com.mvp.mvp_translation_project.exceptions.UserAlreadyExistsException;
import com.mvp.mvp_translation_project.models.Address;
import com.mvp.mvp_translation_project.models.dto.*;
import com.mvp.mvp_translation_project.services.AuthTokenService;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.services.UserService;
import com.mvp.mvp_translation_project.types.RoleType;
import com.mvp.mvp_translation_project.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN') or hasRole('ROOT')")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final AuthTokenService authTokenService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public UserController(UserService userService, EmailService emailService, AuthTokenService authTokenService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.emailService = emailService;
        this.authTokenService = authTokenService;
        this.authenticationFacade = authenticationFacade;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ROOT')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ROOT')")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        // Validar que el ID no sea menor o igual a cero
        if (id <= 0) {
            throw new InvalidDataException("The ID provide is not valid: " + id);
        }
        // Intentar obtener el usuario
        UserDto userDto = userService.getUser(id);

        return ResponseEntity.ok(userDto); // 200 OK
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRequestDto userRegistrationDTO) {
        // Verifica si el correo electrónico ya existe
        if (userService.emailExists(userRegistrationDTO.getEmail())) {
            throw new UserAlreadyExistsException("The email address '"
                    + userRegistrationDTO.getEmail() + "' is already registered.");
        }

        UserDto registeredUser = userService.registerUser(userRegistrationDTO, RoleType.TRANSLATOR);

        // Envía el código al correo electrónico
        emailService.sendSimpleMail(registeredUser.getEmail(), "Welcome to Verbalia, "
                + registeredUser.getName(), "User  created successfully");

        // Invalida el token que se uso para el registro
        authTokenService.invalidateToken(registeredUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {

        Long id = userService.findIdUserByEmail(email);
        userService.softDeleteUser(id);

        return ResponseEntity.noContent().build(); // 204 No Content
    }


    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUserByEmail(
            @RequestParam String email,
            @RequestBody @Valid UserUpdateDto userUpdateDto) {

        UserDto updatedUser = userService.updateUserByDto(email, userUpdateDto);

        return ResponseEntity.ok(updatedUser); // 200 OK
    }


    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String email,
            @RequestParam String currentPass,
            @RequestParam String newPass) {

        // Validar que los datos no sean nulos o vacíos
        if (email == null
                || email.isBlank()
                || currentPass == null
                || currentPass.isBlank()
                || newPass == null
                || newPass.isBlank()) {

            throw new InvalidDataException("Email and passwords cannot be null or empty");
        }

        // Validar que la nueva contraseña sea diferente de la actual
        if (currentPass.equals(newPass)) {
            throw new InvalidDataException("The new password cannot be the same as the current password.");
        }

        // Intentar cambiar la contraseña
        userService.changePassword(email, currentPass, newPass);

        return ResponseEntity.ok("Password updated successfully");
    }


    @GetMapping("/email/{email}")

    public ResponseEntity<UserDto> getUser(@PathVariable String email) {

        if (Boolean.FALSE.equals(Utils.isValidEmail(email))) {
            throw new InvalidPasswordException();
        }
        UserDto user = userService.findUserByEmail(email);

        return ResponseEntity.ok(user); // 200 OK
    }

    @PatchMapping("/update-address")
    public ResponseEntity<String> updateAddress(
            @RequestParam String email,
            @RequestBody @Valid Address address) {

        userService.updateAddress(email, address);

        return ResponseEntity.ok("Address added successfully");
    }


    @GetMapping("/address/{email}")
    public ResponseEntity<Address> getAddressUser(@PathVariable String email) {
        Address address = userService.getAddress(email);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/projects/{email}")
    public ResponseEntity<List<ProjectDto>> getProjectsOfUser(@PathVariable String email) {
        List<ProjectDto> projects = userService.findProjectsByEmail(email);
        return ResponseEntity.ok(projects);
    }

    //funciones para usar desde el usuario auntenticado con el rol TRANSLATOR
    @GetMapping("/my-projects")
    public ResponseEntity<List<ProjectDto>> getProjectsOfAuthUser() {
        String email = authenticationFacade.getAuthenticatedUserEmail();
        List<ProjectDto> projects = userService.findProjectsByEmail(email);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/my-user")
    public ResponseEntity<UserDto> getUser() {
        String email = authenticationFacade.getAuthenticatedUserEmail();
        UserDto userDto = userService.findUserByEmail(email);
        return ResponseEntity.ok(userDto); // 200 OK
    }

    @PatchMapping("/my-update-address")
    public ResponseEntity<String> updateAuthUserAddress(
            @RequestBody @Valid Address address) {

        String email = authenticationFacade.getAuthenticatedUserEmail();
        userService.updateAddress(email, address);
        return ResponseEntity.ok("Address added successfully");
    }

    @PatchMapping("/my-change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String currentPass,
            @RequestParam String newPass) {

        String email = authenticationFacade.getAuthenticatedUserEmail();
        // Validar que los datos no sean nulos o vacíos
        if (currentPass == null
                || currentPass.isBlank()
                || newPass == null
                || newPass.isBlank()) {

            throw new InvalidDataException("Passwords cannot be null or empty");
        }

        // Validar que la nueva contraseña sea diferente de la actual
        if (currentPass.equals(newPass)) {
            throw new InvalidDataException("The new password cannot be the same as the current password.");
        }

        // Intentar cambiar la contraseña
        userService.changePassword(email, currentPass, newPass);

        return ResponseEntity.ok("Password updated successfully");
    }

    @PutMapping("/my-update")
    public ResponseEntity<UserDto> updateAuthUser(
            @RequestBody @Valid UserUpdateDto userUpdateDto) {

        String email = authenticationFacade.getAuthenticatedUserEmail();
        UserDto updatedUser = userService.updateUserByDto(email, userUpdateDto);

        return ResponseEntity.ok(updatedUser); // 200 OK
    }

}

