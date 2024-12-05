package com.mvp.mvp_translation_project.types;

public enum TaskType {
    TRANSLATION("Translation"),
    REVISION("Revision"),
    LQA("LQA"),
    LSO("LSO"),
    DTP("DTP"),
    TRANSCRIPTION("Transcription"),
    SUBTITLED("Subtitled"),
    LOCALIZATION("Localization"),
    TRANSCREATION("Transcreation");

    private final String description;

    TaskType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

