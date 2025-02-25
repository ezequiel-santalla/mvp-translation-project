package com.mvp.mvp_translation_project.repositories;

import com.mvp.mvp_translation_project.models.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RevokedTokenRepository extends JpaRepository <RevokedToken, Long> {

    Optional<RevokedToken> findByToken(String token);
}
