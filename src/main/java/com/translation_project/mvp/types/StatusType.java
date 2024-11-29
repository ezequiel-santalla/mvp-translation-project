package com.translation_project.mvp.types;

import lombok.Getter;

@Getter
public enum StatusType {
    ASSIGNED("Asignado"),
    IN_PROCESS("En proceso"),
    UNDER_REVIEW("En revisión"),
    FINISHED("Finalizado");

    private final String description;

    StatusType(String description) {
        this.description = description;
    }
}
