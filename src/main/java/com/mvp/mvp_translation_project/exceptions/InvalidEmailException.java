package com.mvp.mvp_translation_project.exceptions;

public class InvalidEmailException extends RuntimeException {


    public InvalidEmailException(String invalidEmail) {
        super("The email '"+invalidEmail+"' is not in a valid format");
    }
}
