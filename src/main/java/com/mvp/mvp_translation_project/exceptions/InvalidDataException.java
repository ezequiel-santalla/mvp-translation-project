package com.mvp.mvp_translation_project.exceptions;

import jakarta.validation.Valid;

import java.util.List;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(List<String> validationErrors) {
        super("The data provided is not valid. "+validationErrors);
    }
}
