package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.StatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects", indexes = {
        @Index(name = "idx_project_name", columnList = "name"),
        @Index(name = "idx_project_status", columnList = "status")
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime startingDate;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime deadline;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime finishedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status;

    @Column(nullable = false, length = 200)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User translator;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_language_pair", nullable = false)
    private LanguagePair languagePair;
}
