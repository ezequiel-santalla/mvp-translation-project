package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.exceptions.InvalidDataException;
import com.mvp.mvp_translation_project.exceptions.InvalidPasswordException;
import com.mvp.mvp_translation_project.exceptions.UserAlreadyExistsException;
import com.mvp.mvp_translation_project.models.Address;
import com.mvp.mvp_translation_project.models.dtos.languages.LanguagePairDto;
import com.mvp.mvp_translation_project.models.dtos.projects.ProjectDto;
import com.mvp.mvp_translation_project.models.dtos.users.UserDto;
import com.mvp.mvp_translation_project.models.dtos.users.UserRequestDto;
import com.mvp.mvp_translation_project.models.dtos.users.UserUpdateDto;
import com.mvp.mvp_translation_project.services.AuthCodeService;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.services.UserService;
import com.mvp.mvp_translation_project.types.RoleType;
import com.mvp.mvp_translation_project.types.AuthCodeType;
import com.mvp.mvp_translation_project.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "https://e447-190-189-40-246.ngrok-free.app")

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserAdminController {

    private final UserService userService;
    private final EmailService emailService;
    private final AuthCodeService authTokenService;

    @Autowired
    public UserAdminController(UserService userService, EmailService emailService, AuthCodeService authTokenService) {
        this.userService = userService;
        this.emailService = emailService;
        this.authTokenService = authTokenService;
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRequestDto userRegistrationDTO) {
        // Verifica si el correo electrónico ya existe
        if (userService.emailExists(userRegistrationDTO.getEmail())) {
            throw new UserAlreadyExistsException("The email address '"
                    + userRegistrationDTO.getEmail() + "' is already registered.");
        }

        UserDto registeredUser = userService.registerUser(userRegistrationDTO, RoleType.ROLE_TRANSLATOR);

        // Envía el código al correo electrónico
        emailService.sendSimpleMail(registeredUser.getEmail(), "Welcome to Verbalia, "
                + registeredUser.getName(), "User  created successfully");

        // Invalida el token que se uso para el registro
        authTokenService.invalidateCode(registeredUser.getEmail(), AuthCodeType.PRE_REGISTRATION);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }


    @DeleteMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {

        Long id = userService.findIdUserByEmail(email);
        userService.softDeleteUser(id);

        return ResponseEntity.noContent().build(); // 204 No Content
    }


    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<UserDto> updateUserByEmail(
            @RequestParam String email,
            @RequestBody @Valid UserUpdateDto userUpdateDto) {

        UserDto updatedUser = userService.updateUserByDto(email, userUpdateDto);

        return ResponseEntity.ok(updatedUser); // 200 OK
    }


    @PatchMapping("/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {

        if (Boolean.FALSE.equals(Utils.isValidEmail(email))) {
            throw new InvalidPasswordException();
        }
        UserDto user = userService.findUserByEmail(email);

        return ResponseEntity.ok(user); // 200 OK
    }

    @PatchMapping("/update-address")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<String> updateAddress(
            @RequestParam String email,
            @RequestBody @Valid Address address) {

        userService.updateAddress(email, address);

        return ResponseEntity.ok("Address added successfully");
    }


    @GetMapping("/address/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<Address> getAddressUser(@PathVariable String email) {
        Address address = userService.getAddress(email);
        return ResponseEntity.ok(address);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    @GetMapping("/projects/{email}")
    public ResponseEntity<List<ProjectDto>> getProjectsOfUser(@PathVariable String email) {
        List<ProjectDto> projects = userService.findProjectsByEmail(email);
        return ResponseEntity.ok(projects);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    @GetMapping("/languages/{email}")
    public ResponseEntity<List<LanguagePairDto>> getLanguagePairsOfUser(@PathVariable String email) {
        List<LanguagePairDto> languages = userService.findLanguagesByEmail(email);
        return ResponseEntity.ok(languages);
    }
}

