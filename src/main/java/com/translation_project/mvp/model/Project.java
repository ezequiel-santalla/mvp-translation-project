package com.translation_project.mvp.model;

import com.translation_project.mvp.types.StatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter @Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime startDate;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
}

