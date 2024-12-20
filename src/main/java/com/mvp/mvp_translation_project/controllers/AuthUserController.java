package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.User;
import com.mvp.mvp_translation_project.models.dto.LoginRequest;
import com.mvp.mvp_translation_project.services.JWTService;
import com.mvp.mvp_translation_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-user")
public class AuthUserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    public AuthUserController(AuthenticationManager authenticationManager, UserService userService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
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
            return ResponseEntity.status(401).body("Invalid credentials"+e.getMessage());
        }
    }


}
