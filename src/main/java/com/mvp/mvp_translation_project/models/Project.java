package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.StatusType;
import com.mvp.mvp_translation_project.types.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "projects", indexes = {
        @Index(name = "idx_project_name", columnList = "name"),
        @Index(name = "idx_project_status", columnList = "status")
})
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType taskType;

    // Relación con ProjectPayment
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_payment", referencedColumnName = "id")
    private ProjectPayment projectPayment;

    // Relación con User (Traductor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User translator;

    // Relación con LanguagePair (Solo un par de idiomas por proyecto)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_language_pair", nullable = false)
    private LanguagePair languagePair;
}
