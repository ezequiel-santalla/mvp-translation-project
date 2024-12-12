package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.exceptions.InvalidEmailException;
import com.mvp.mvp_translation_project.services.AuthTokenService;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTokenController {

    private final AuthTokenService authTokenService;
    private final EmailService emailService;

    @Autowired
    public AuthTokenController(AuthTokenService authTokenService, EmailService emailService) {
        this.authTokenService = authTokenService;
        this.emailService = emailService;
    }

    @GetMapping("/generate-pre-register-token")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> generatePreRegisterToken(@RequestParam String email) {

        if (!Utils.isValidEmail(email)) {
            throw new InvalidEmailException(email);
        }

        String preRegistrationToken = authTokenService.createPreRegistrationToken(email);
        emailService.sendPreRegisterEmail(email, preRegistrationToken);

        return ResponseEntity.ok("Pre-registration email sent successfully to " + email);
    }

    @GetMapping("generate-recovery-token")
    public ResponseEntity<String> generateRecoveryToken(@RequestParam String email) {

        if (!Utils.isValidEmail(email)) {
            throw new InvalidEmailException(email);
        }

        String recoveryToken = authTokenService.createRecoveryToken(email);
        emailService.sendPreRegisterEmail(email, recoveryToken);

        return ResponseEntity.ok("Recovery email sent successfully to " + email);
    }

    @GetMapping("/validate-registration")
    public ResponseEntity<String> validateRegistration(
            @RequestParam String email,
            @RequestParam String token) {

        authTokenService.validateRegistration(email, token);

        return ResponseEntity.ok("Registration confirmed successfully.");
    }

}
