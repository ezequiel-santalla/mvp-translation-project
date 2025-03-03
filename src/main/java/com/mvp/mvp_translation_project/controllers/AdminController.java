package com.mvp.mvp_translation_project.controllers;
import com.mvp.mvp_translation_project.exceptions.UserAlreadyExistsException;
import com.mvp.mvp_translation_project.models.dto.ChangeEmailRequest;
import com.mvp.mvp_translation_project.models.dto.UserDto;
import com.mvp.mvp_translation_project.models.dto.UserRequestDto;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.services.UserService;
import com.mvp.mvp_translation_project.types.RoleType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public AdminController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    @GetMapping("/users/get-all")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PostMapping("/register-admin")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROOT')")
    public ResponseEntity<UserDto> registerUser(
            @RequestBody @Valid UserRequestDto userRegistrationDTO) {

        // Verifica si el correo electrónico ya existe
        if (userService.emailExists(userRegistrationDTO.getEmail())) {
            throw new UserAlreadyExistsException("The email address '"
                    + userRegistrationDTO.getEmail() + "' is already registered.");
        }

        UserDto registeredUser = userService.registerUser(userRegistrationDTO, RoleType.ROLE_ADMIN);

        // Envía el código al correo electrónico
        emailService.sendSimpleMail(registeredUser.getEmail(), "Welcome to Verbalia, "
                + registeredUser.getName(), "Admin User created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PutMapping("/promote-to-admin")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ROOT')")
    public ResponseEntity<UserDto> toAdmin(
            @RequestParam String email) {

        UserDto updatedUser = userService.updateUserRole(email, RoleType.ROLE_ADMIN);

        return ResponseEntity.ok(updatedUser); // 200 OK
    }


    @PutMapping("/users/email")
    @PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
    public ResponseEntity<String> changeUserEmail(@RequestBody ChangeEmailRequest request) {

        boolean success = userService.changeUserEmail(request.oldEmail(), request.newEmail());

        if (success) {
            return ResponseEntity.ok("Email updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update email.");
        }
    }

}