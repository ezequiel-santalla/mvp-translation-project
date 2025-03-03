package com.mvp.mvp_translation_project.models;

import com.mvp.mvp_translation_project.types.AuthCodeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "auth_codes")
public class AuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime expiration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthCodeType codeType;

    @Column(nullable = false)
    private Boolean used;
}
