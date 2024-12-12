package com.mvp.mvp_translation_project.repositories;

import com.mvp.mvp_translation_project.models.LanguagePair;
import com.mvp.mvp_translation_project.types.LanguageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguagePairRepository extends JpaRepository<LanguagePair, Long> {
    List<LanguagePair> findByActiveTrue();
    Optional<LanguagePair> findBySourceLanguageAndTargetLanguage(LanguageType sourceLanguage, LanguageType targetLanguage);

}
