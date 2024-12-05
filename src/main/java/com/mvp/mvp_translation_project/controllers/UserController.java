package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Verificar si el correo electrónico ya existe
        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

            // Guardar el usuario
            User createdUser = userService.createUser(user);

            // Enviar el código al correo electrónico
            emailService.sendSimpleMail(user.getEmail(), "Welcome to Verbalia", "User created successfully");

            // Retornar el usuario creado con el código de estado CREATED
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

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
