package com.mvp.mvp_translation_project.exceptions;

public class AuthTokenNotFoundException extends RuntimeException {

    public AuthTokenNotFoundException(String email) {
        super("Authorization Code not found with Email "+ email);
    }

    public AuthTokenNotFoundException() {
        super("Authorization Code not found");
    }
}
