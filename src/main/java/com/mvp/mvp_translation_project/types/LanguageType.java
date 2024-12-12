package com.mvp.mvp_translation_project.types;

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
    DANISH("da", "dansk", "🇩🇰"),
    SWEDISH("sv", "svenska", "🇸🇪"),
    BENGALI("bn", "বাংলা", "🇧🇩"),
    KAZAKH("kk", "қазақ", "🇰🇿"),
    LUXEMBOURGISH("lb", "Lëtzebuergesch", "🇱🇺"),
    INDONESIAN("id", "Bahasa Indonesia", "🇮🇩"),
    NORWEGIAN("no", "norsk", "🇳🇴"),
    CATALAN("ca", "català", "🇪🇸");

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
}
