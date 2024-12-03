package com.mvp.mvp_translation_project.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "languages", indexes = {
        @Index(name = "idx_language_code", columnList = "codeIso")
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter @Setter
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 5)
    private String codeIso;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
}
