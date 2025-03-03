package com.mvp.mvp_translation_project.repositories;

import com.mvp.mvp_translation_project.models.AuthCode;
import com.mvp.mvp_translation_project.types.AuthCodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {

    @Query("SELECT a FROM AuthCode a WHERE a.expiration > CURRENT_TIMESTAMP " +
            "AND a.used = false " +
            "AND a.email = :email " +
            "AND a.codeType = :codeType")
    Optional<AuthCode> findValidCodeByEmailAndType(
            @Param("email") String email,
            @Param("codeType") AuthCodeType codeType);

    //Optional<AuthToken> findTokenByEmailAndUsed(String email);

    //@Query("SELECT a FROM AuthToken a WHERE a.email = :email AND a.expiration > :currentDateTime AND a.used = false")

    //Optional<AuthToken> findValidAuthTokenByEmail(@Param("email") String email, @Param("currentDateTime") LocalDateTime currentDateTime);


}

