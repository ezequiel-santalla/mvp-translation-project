package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.UserDTO;
import com.mvp.mvp_translation_project.models.UserRegistrationDTO;
import com.mvp.mvp_translation_project.models.UserResponseDTO;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser (@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        // Verificar si el correo electrónico ya existe
        if (userService.emailExists(userRegistrationDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        try {
            // Registrar al usuario
            UserResponseDTO registeredUser  = userService.registerUser (userRegistrationDTO);
            // Enviar el código al correo electrónico
            emailService.sendSimpleMail(registeredUser.getEmail(), "Welcome to Verbalia, "+registeredUser.getName(), "User created successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Registrar el error (puedes usar un logger aquí)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    // 5. Eliminar un usuario por ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.findUsersByEmail(email));
    }


}
