package com.mvp.mvp_translation_project.repositories;

import com.mvp.mvp_translation_project.models.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    @Query("SELECT a FROM AuthToken a WHERE a.expiration > CURRENT_TIMESTAMP " +
            "AND a.used = false " +
            "AND a.email = :email")
    Optional<AuthToken> findValidTokenByEmail(@Param("email") String email);

    //Optional<AuthToken> findTokenByEmailAndUsed(String email);

    //@Query("SELECT a FROM AuthToken a WHERE a.email = :email AND a.expiration > :currentDateTime AND a.used = false")

    //Optional<AuthToken> findValidAuthTokenByEmail(@Param("email") String email, @Param("currentDateTime") LocalDateTime currentDateTime);


}

