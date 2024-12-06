package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_identity", columnList = "identityNumber")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, unique = true, length = 20)
    @EqualsAndHashCode.Include
    private String identityNumber;

    @Column(nullable = false, unique = true, length = 100)
    @EqualsAndHashCode.Include
    private String email;

    @Column(nullable = false, unique = true, length = 30)
    private String cellphone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active = true;

    // Relación Address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", referencedColumnName = "id")
    private Address address;

    // Relación con Project
    @OneToMany(mappedBy = "translator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Project> projects = new HashSet<>();

    // Relación con LanguagePair
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_language_pairs",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_language_pair")
    )
    private Set<LanguagePair> languagePairs = new HashSet<>();
}