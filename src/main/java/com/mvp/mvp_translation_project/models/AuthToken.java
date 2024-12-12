package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "auth_tokens")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String token;

    @Column(nullable = false, length = 100)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(nullable = false)
    private Boolean used;
}
