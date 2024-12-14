package com.mvp.mvp_translation_project.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(Long id) {
        super("There is already a user registered with ID '"+id+"'");
    }
    public UserAlreadyExistsException(String email) {
        super("There is already a user registered with email '"+email+"'");
    }

    public UserAlreadyExistsException() {
        super("There is already a user registered with that email");
    }
}
