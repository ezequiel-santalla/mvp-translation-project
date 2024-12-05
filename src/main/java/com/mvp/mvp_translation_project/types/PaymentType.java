package com.mvp.mvp_translation_project.types;

public enum PaymentType {
    PER_WORD("Per word"),
    PER_HOUR("Per hour"),
    PER_MINUTE("Per minute"),
    FLAT("Flat fee");

    private final String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

