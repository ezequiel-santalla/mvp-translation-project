package com.mvp.mvp_translation_project.types;

import lombok.Getter;

@Getter
public enum PaymentType {
    PER_WORD("Per word"),
    PER_HOUR("Per hour"),
    PER_MINUTE("Per minute"),
    PER_PAGE("Per page");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

}

