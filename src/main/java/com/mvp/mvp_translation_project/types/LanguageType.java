package com.mvp.mvp_translation_project.types;

public enum LanguageType {
    SPANISH("es", "español"),
    ENGLISH("en", "english"),
    ITALIAN("it", "italiano"),
    FRENCH("fr", "français"),
    PORTUGUESE("pt", "português"),
    GERMAN("de", "Deutsch"),
    RUSSIAN("ru", "русский"),
    CHINESE("zh", "中文"),
    KOREAN("ko", "한국어"),
    JAPANESE("ja", "日本語"),
    MARATHI("mr", "मराठी"),
    HINDI("hi", "हिन्दी"),
    TELUGU("te", "తెలుగు"),
    SWAHILI("sw", "Kiswahili"),
    DANISH("da", "dansk"),
    SWEDISH("sv", "svenska"),
    BENGALI("bn", "বাংলা"),
    KAZAKH("kk", "қазақ"),
    LUXEMBOURGISH("lb", "Lëtzebuergesch"),
    INDONESIAN("id", "Bahasa Indonesia"),
    NORWEGIAN("no", "norsk"),
    CATALAN("ca", "català");

    private final String codeIso;
    private final String name;

    LanguageType(String codeIso, String name) {
        this.codeIso = codeIso;
        this.name = name;
    }

    public String getCodeIso() {
        return codeIso;
    }

    public String getName() {
        return name;
    }
}