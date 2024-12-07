package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.exceptions.InvalidPasswordException;
import com.mvp.mvp_translation_project.exceptions.UserNotFoundException;
import com.mvp.mvp_translation_project.models.UserDto;
import com.mvp.mvp_translation_project.models.UserRequestDto;
import com.mvp.mvp_translation_project.models.UserUpdateDto;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.services.UserService;
import com.mvp.mvp_translation_project.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        // Validar que el ID no sea menor o igual a cero
        if (id <= 0) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        try {
            // Intentar obtener el usuario
            UserDto userDto = userService.getUser(id);
            return ResponseEntity.ok(userDto); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404 Not Found
        }
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(
            @RequestBody @Valid UserRequestDto userRegistrationDTO) {
        // Verifica si el correo electronico ya existe
        if (userService.emailExists(userRegistrationDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        try {
            UserDto registeredUser = userService.registerUser(userRegistrationDTO);
            // Envia el código al correo electrónico
            emailService.sendSimpleMail(registeredUser.getEmail(), "Welcome to Verbalia, "
                    + registeredUser.getName(), "User created successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {

        Long id = userService.findIdUserByEmail(email);

        try {
            userService.softDeleteUser(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }


    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUserByEmail(
            @RequestParam String email,
            @RequestBody @Valid UserUpdateDto userUpdateDto) {
        try {
            UserDto updatedUser = userService.updateUserByDto(email, userUpdateDto);
            return ResponseEntity.ok(updatedUser); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404 Not Found
                    .body(null);
        } catch (Exception e) {
            // Manejo de otras excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
                    .body(null);
        }
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String email,
            @RequestParam String currentPass,
            @RequestParam String newPass) {
        try {
            userService.changePassword(email, currentPass, newPass);

            return ResponseEntity.ok("Password updated successfully");

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidPasswordException e) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/email/{email}")

    public ResponseEntity<UserDto> getUser(@PathVariable String email) {

        if (Boolean.FALSE.equals(Utils.isValidEmail(email))) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        try {
            UserDto user = userService.findUserByEmail(email);
            return ResponseEntity.ok(user); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

}