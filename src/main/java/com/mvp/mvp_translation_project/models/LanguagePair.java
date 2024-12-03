package com.mvp.mvp_translation_project.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "language_pairs", indexes = {
        @Index(name = "idx_source_language", columnList = "id_source_language"),
        @Index(name = "idx_target_language", columnList = "id_target_language")
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter @Setter
public class LanguagePair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_source_language", nullable = false)
    private Language sourceLanguage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_target_language", nullable = false)
    private Language targetLanguage;

    @ManyToMany(mappedBy = "languagePairs", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "languagePair", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();
}
