package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.dtos.auth.LoginRequest;
import com.mvp.mvp_translation_project.models.dtos.auth.ResetPasswordRequest;
import com.mvp.mvp_translation_project.models.dtos.auth.ValidateRegistrationRequest;
import com.mvp.mvp_translation_project.services.*;
import com.mvp.mvp_translation_project.types.AuthCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth-user")
public class AuthUserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTService jwtService;
    private final TokenService tokenService;
    private final AuthCodeService authTokenService;
    private final EmailService emailService;
    private final AuthCodeService authCodeService;


    @Autowired
    public AuthUserController(AuthenticationManager authenticationManager, UserService userService, JWTService jwtService, TokenService tokenService, AuthCodeService authTokenService, EmailService emailService, AuthCodeService authCodeService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
        this.authTokenService = authTokenService;
        this.emailService = emailService;
        this.authCodeService = authCodeService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            User user = userService.getUserByEmail(loginRequest.email());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtService.generateJwtToken(authentication.getName(), user.getRole());
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials" + e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("No valid token provided.");
        }

        String token = authHeader.substring(7);
        tokenService.revokeToken(token);

        return ResponseEntity.ok("Logout successful. Token revoked.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {

        emailService.sendRecoveryEmail(email, authTokenService.createRecoveryCode(email));
        return ResponseEntity.ok("Email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest) {

        authTokenService.resetPassword(
                resetPasswordRequest.email(),
                resetPasswordRequest.recoveryToken(),
                resetPasswordRequest.newPassword());
        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/validate-registration")
    public ResponseEntity<String> validateRegistration(
            @RequestBody ValidateRegistrationRequest validateRegistrationRequest) {

        if(Boolean.TRUE.equals(authCodeService.validateCode(
                validateRegistrationRequest.email(),
                validateRegistrationRequest.registrationCode(),
                AuthCodeType.PRE_REGISTRATION))){
            return ResponseEntity.ok("Registration validate successfully.");
        }

        return ResponseEntity.badRequest().body("Invalid Code");
    }

}
