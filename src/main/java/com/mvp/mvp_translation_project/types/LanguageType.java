package com.mvp.mvp_translation_project.types;

import com.mvp.mvp_translation_project.models.dto.LanguageDto;

public enum LanguageType {
    SPANISH("es", "español", "es"),
    ENGLISH("en", "english", "gb"),
    ITALIAN("it", "italiano", "it"),
    FRENCH("fr", "français", "fr"),
    PORTUGUESE("pt", "português", "pt"),
    GERMAN("de", "Deutsch", "de"),
    RUSSIAN("ru", "русский", "ru"),
    CHINESE("zh", "中文", "cn"),
    KOREAN("ko", "한국어", "kr"),
    JAPANESE("ja", "日本語", "jp"),
    MARATHI("mr", "मराठी", "in"),
    HINDI("hi", "हिन्दी", "in"),
    TELUGU("te", "తెలుగు", "in"),
    SWAHILI("sw", "Kiswahili", "ke"),
    DANISH("da", "dansk", "dk"),
    SWEDISH("sv", "svenska", "se"),
    BENGALI("bn", "বাংলা", "bd"),
    KAZAKH("kk", "қазақ", "kz"),
    LUXEMBOURGISH("lb", "Lëtzebuergesch", "lu"),
    INDONESIAN("id", "Bahasa Indonesia", "id"),
    NORWEGIAN("no", "norsk", "no"),
    CATALAN("ca", "català", "es");

    private final String codeIso;
    private final String name;
    private final String flagCode;

    LanguageType(String codeIso, String name, String flagCode) {
        this.codeIso = codeIso;
        this.name = name;
        this.flagCode = flagCode;
    }

    public String getCodeIso() {
        return codeIso;
    }

    public String getName() {
        return name;
    }

    public String getFlagCode() {
        return flagCode;
    }

    public LanguageDto toDto() {
        return new LanguageDto(this.name(), this.codeIso, this.name, this.flagCode);
    }
}
