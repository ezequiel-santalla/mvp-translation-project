package com.mvp.mvp_translation_project.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long id) {
        super("Project was not found with ID " + id);
    }

    public ProjectNotFoundException() {
        super("Project was not found");
    }
}
