package com.mvp.mvp_translation_project.exceptions;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }

    public InvalidEmailException() {
        super("The email is not in a valid format");
    }
}
