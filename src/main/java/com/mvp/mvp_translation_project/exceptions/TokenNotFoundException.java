package com.mvp.mvp_translation_project.exceptions;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String email) {
        super("Token was not found with Email "+ email);
    }

    public TokenNotFoundException() {
        super("Token was not found");
    }
}
