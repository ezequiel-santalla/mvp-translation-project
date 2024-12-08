package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.LanguageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter @Setter
@Entity
@Table(name = "language_pairs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"source_language", "target_language"})
        },
        indexes = {
                @Index(name = "idx_source_language", columnList = "source_language"),
                @Index(name = "idx_target_language", columnList = "target_language")
        })
public class LanguagePair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_language", nullable = false, length = 20)
    private LanguageType sourceLanguage;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_language", nullable = false, length = 20)
    private LanguageType targetLanguage;

    @ManyToMany(mappedBy = "languagePairs", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> users = new ArrayList<>();
}

