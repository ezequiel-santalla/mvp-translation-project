package com.mvp.mvp_translation_project.models.dtos.languages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LanguageDto {

    private String value;
    private String codeIso;
    private String nativeName;
    private String flagCode;
    private String englishName;
}
