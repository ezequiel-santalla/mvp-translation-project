package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.DataAccessRuntimeException;
import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.repositories.LanguagePairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LanguagePairService {

    private final LanguagePairRepository languagePairRepository;

    @Autowired
    public LanguagePairService(LanguagePairRepository languagePairRepository) {
        this.languagePairRepository = languagePairRepository;
    }

    public List<LanguagePair> getAllLanguagePairs() {
        try {
            return languagePairRepository.findAll();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to retrieve language pairs list", e);
        }
    }

    public List<LanguagePair> getActiveLanguagePairs() {
        try {
            return languagePairRepository.findByActiveTrue();
        } catch (DataAccessException e) {
            throw new DataAccessRuntimeException("Failed to retrieve user list", e);
        }
    }

    public LanguagePair findOrCreateLanguagePair(LanguagePair inputPair) {
        if (inputPair.getSourceLanguage().equals(inputPair.getTargetLanguage())) {
            System.out.println("Los idiomas no pueden ser iguales");
            return null;
        }

        return languagePairRepository.findBySourceLanguageAndTargetLanguage(
                inputPair.getSourceLanguage(),
                inputPair.getTargetLanguage()
        ).orElseGet(() -> {
            LanguagePair newPair = new LanguagePair();
            newPair.setSourceLanguage(inputPair.getSourceLanguage());
            newPair.setTargetLanguage(inputPair.getTargetLanguage());
            return languagePairRepository.save(newPair);
        });
    }

    public void softDeleteLanguagePair(Long id) {

        Optional<LanguagePair> languagePairOptional = languagePairRepository.findById(id);

        if (languagePairOptional.isPresent()) {
            LanguagePair languagePair = languagePairOptional.get();

            languagePair.setActive(false);

            languagePairRepository.save(languagePair);
        } else {
            // Language Pair no encontrado
            System.out.println("Language Pair Not Found");
        }
    }
}
