package com.mvp.mvp_translation_project.exceptions;

import org.springframework.dao.DataAccessException;

public class DataAccessRuntimeException extends RuntimeException {
    public DataAccessRuntimeException(String s, DataAccessException e) {
    }
}
