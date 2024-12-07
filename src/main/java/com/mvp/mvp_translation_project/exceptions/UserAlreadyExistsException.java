package com.mvp.mvp_translation_project.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException() {
        super("There is already a user registered with that email");
    }
}
