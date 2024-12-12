package com.mvp.mvp_translation_project.exceptions;

public class InvalidAuthTokenException extends RuntimeException {

    public InvalidAuthTokenException(String message) {
        super(message);
    }

    public InvalidAuthTokenException() {
        super("The authentication token is invalid or has expired");
    }
}