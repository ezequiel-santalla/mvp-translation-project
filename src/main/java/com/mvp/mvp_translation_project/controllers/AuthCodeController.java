package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.exceptions.InvalidAuthTokenException;
import com.mvp.mvp_translation_project.exceptions.InvalidEmailException;
import com.mvp.mvp_translation_project.services.AuthCodeService;
import com.mvp.mvp_translation_project.services.EmailService;
import com.mvp.mvp_translation_project.types.AuthCodeType;
import com.mvp.mvp_translation_project.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth-code")
public class AuthCodeController {

    private final AuthCodeService authCodeService;
    private final EmailService emailService;

    @Autowired
    public AuthCodeController(AuthCodeService authCodeService, EmailService emailService) {
        this.authCodeService = authCodeService;
        this.emailService = emailService;
    }

    @PostMapping("/generate-pre-register-code")
    public ResponseEntity<String> generatePreRegisterCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (!Utils.isValidEmail(email)) {
            throw new InvalidEmailException(email);
        }
        String preRegistrationCode = authCodeService.createPreRegistrationCode(email);
        emailService.sendPreRegisterEmail(email, preRegistrationCode);
        return ResponseEntity.ok("Pre-registration email sent successfully to " + email);
    }

}
