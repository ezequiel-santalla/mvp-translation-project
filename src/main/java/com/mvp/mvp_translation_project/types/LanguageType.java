package com.mvp.mvp_translation_project.types;

import com.mvp.mvp_translation_project.models.dtos.languages.LanguageDto;
import lombok.Getter;

@Getter
public enum LanguageType {
    SPANISH("es", "español", "es", "Spanish"),
    ENGLISH("en", "english", "gb", "English"),
    ITALIAN("it", "italiano", "it", "Italian"),
    FRENCH("fr", "français", "fr", "French"),
    PORTUGUESE("pt", "português", "pt", "Portuguese"),
    GERMAN("de", "Deutsch", "de", "German"),
    RUSSIAN("ru", "русский", "ru", "Russian"),
    CHINESE("zh", "中文", "cn", "Chinese"),
    KOREAN("ko", "한국어", "kr", "Korean"),
    JAPANESE("ja", "日本語", "jp", "Japanese"),
    MARATHI("mr", "मराठी", "in", "Marathi"),
    HINDI("hi", "हिन्दी", "in", "Hindi"),
    TELUGU("te", "తెలుగు", "in", "Telugu"),
    SWAHILI("sw", "Kiswahili", "ke", "Swahili"),
    DANISH("da", "dansk", "dk", "Danish"),
    SWEDISH("sv", "svenska", "se", "Swedish"),
    BENGALI("bn", "বাংলা", "bd", "Bengali"),
    KAZAKH("kk", "қазақ", "kz", "Kazakh"),
    LUXEMBOURGISH("lb", "Lëtzebuergesch", "lu", "Luxembourgish"),
    INDONESIAN("id", "Bahasa Indonesia", "id", "Indonesian"),
    NORWEGIAN("no", "norsk", "no", "Norwegian"),
    CATALAN("ca", "català", "es", "Catalan");

    private final String codeIso;
    private final String nativeName; // Nombre en el idioma original
    private final String flagCode;
    private final String englishName; // Nombre en inglés

    LanguageType(String codeIso, String nativeName, String flagCode, String englishName) {
        this.codeIso = codeIso;
        this.nativeName = nativeName;
        this.flagCode = flagCode;
        this.englishName = englishName;
    }

    public LanguageDto toDto() {
        return new LanguageDto(this.name(), this.codeIso, this.nativeName, this.flagCode, this.englishName);
    }
}
