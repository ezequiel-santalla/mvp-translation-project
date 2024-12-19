package com.mvp.mvp_translation_project.models.dto;

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
    private String name;
    private String flagCode;
}
