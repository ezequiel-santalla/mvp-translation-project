package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.dtos.languages.LanguageDto;
import com.mvp.mvp_translation_project.types.LanguageType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "https://e447-190-189-40-246.ngrok-free.app")

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/languages")
@PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
public class LanguageController {

    @GetMapping("/get-all")
    public ResponseEntity<List<LanguageDto>> getAllLanguages() {
        List<LanguageDto> languages = Arrays.stream(LanguageType.values())
                .map(LanguageType::toDto)
                .toList();
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/get-all-names")
    public ResponseEntity<List<String>> getAllLanguageNames() {
        List<String> languages = Arrays.stream(LanguageType.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/get-filtered-list/{language}")
    public ResponseEntity<List<LanguageDto>> getFilteredLanguagesList(
            @PathVariable String language) {
        List<LanguageDto> languages = Arrays.stream(LanguageType.values())
                .filter(lang -> !lang.name().equalsIgnoreCase(language))
                .map(LanguageType::toDto)
                .toList();
        return ResponseEntity.ok(languages);
    }


    @GetMapping("/get-filtered-list-names/{language}")
    public ResponseEntity<List<String>> getFilteredLanguageNamesList(
            @PathVariable String language) {
        List<String> languages = Arrays.stream(LanguageType.values())
                .map(Enum::name)
                .filter(name -> !name.equalsIgnoreCase(language))
                .toList();
        return ResponseEntity.ok(languages);
    }

}
