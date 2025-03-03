package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.exceptions.InvalidDataException;
import com.mvp.mvp_translation_project.models.Address;
import com.mvp.mvp_translation_project.models.dto.*;
import com.mvp.mvp_translation_project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users/me")
public class UserProfileController {

    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public UserProfileController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    //funciones para usar desde el usuario auntenticado con el rol TRANSLATOR
    @GetMapping("/projects")
    @PreAuthorize("hasAnyRole('TRANSLATOR', 'ADMIN', 'ROOT')")
    public ResponseEntity<List<ProjectDto>> getProjectsOfAuthUser() {
        String email = authenticationFacade.getAuthenticatedUserEmail();
        List<ProjectDto> projects = userService.findProjectsByEmail(email);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('TRANSLATOR', 'ADMIN', 'ROOT')")
    public ResponseEntity<UserDto> getUser() {
        String email = authenticationFacade.getAuthenticatedUserEmail();
        System.out.println("email"+email);
        UserDto userDto = userService.findUserByEmail(email);
        return ResponseEntity.ok(userDto); // 200 OK
    }

    @PatchMapping("/update-address")
    @PreAuthorize("hasAnyRole('TRANSLATOR', 'ADMIN', 'ROOT')")
    public ResponseEntity<String> updateAuthUserAddress(
            @RequestBody @Valid Address address) {

        String email = authenticationFacade.getAuthenticatedUserEmail();
        userService.updateAddress(email, address);
        return ResponseEntity.ok("Address added successfully");
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasAnyRole('TRANSLATOR', 'ADMIN', 'ROOT')")
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

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('TRANSLATOR', 'ADMIN', 'ROOT')")
    public ResponseEntity<UserDto> updateAuthUser(
            @RequestBody @Valid UserUpdateDto userUpdateDto) {

        String email = authenticationFacade.getAuthenticatedUserEmail();
        UserDto updatedUser = userService.updateUserByDto(email, userUpdateDto);

        return ResponseEntity.ok(updatedUser); // 200 OK
    }

}

