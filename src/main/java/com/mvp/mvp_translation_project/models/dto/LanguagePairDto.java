package com.mvp.mvp_translation_project.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LanguagePairDto {

    private LanguageDto sourceLanguage;
    private LanguageDto targetLanguage;

}
