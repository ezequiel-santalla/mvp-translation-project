package com.mvp.mvp_translation_project.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super("User was not found with ID " + id);
    }

    public UserNotFoundException() {
        super("User was not found");
    }
}
