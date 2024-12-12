package com.mvp.mvp_translation_project.controllers;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.models.dto.UserDto;
import com.mvp.mvp_translation_project.services.LanguagePairService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/language-pairs")
public class LanguagePairController {

    private final LanguagePairService languagePairService;

    @Autowired
    public LanguagePairController(LanguagePairService languagePairService) {
        this.languagePairService = languagePairService;
    }

    @GetMapping
    public ResponseEntity<List<LanguagePair>> getAllLanguagePairs() {
        return ResponseEntity.ok(languagePairService.getActiveLanguagePairs());
    }

    @PostMapping("/register")
    public ResponseEntity<LanguagePair> postLanguagePair(@RequestBody @Valid LanguagePair languagePair) {
        languagePairService.findOrCreateLanguagePair(languagePair);

        return ResponseEntity.status(HttpStatus.CREATED).body(languagePair);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        languagePairService.softDeleteLanguagePair(id);

        return ResponseEntity.noContent().build();
    }
}
