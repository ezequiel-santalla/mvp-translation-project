package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.InvalidAuthTokenException;
import com.mvp.mvp_translation_project.models.AuthToken;
import com.mvp.mvp_translation_project.repositories.AuthTokenRepository;
import com.mvp.mvp_translation_project.types.TokenType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter

@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer TOKEN_LENGTH = 6;

    @Autowired
    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    @Transactional
    public String createRecoveryToken(String email) {

        invalidateToken(email);
        AuthToken recoveryToken = new AuthToken();
        String token = generateToken();

        recoveryToken.setEmail(email);
        recoveryToken.setToken(generateHash(token));
        recoveryToken.setExpiration(LocalDateTime.now().plusMinutes(20));
        recoveryToken.setTokenType(TokenType.RECOVERY);
        recoveryToken.setUsed(false);

        return token;
    }


    public String createPreRegistrationToken(String email) {

        AuthToken preRegistrationToken = new AuthToken();
        String token = generateToken();

        preRegistrationToken.setEmail(email);
        preRegistrationToken.setToken(generateHash(token));
        preRegistrationToken.setExpiration(LocalDateTime.now().plusDays(2));
        preRegistrationToken.setTokenType(TokenType.PRE_REGISTRATION);
        preRegistrationToken.setUsed(false);

        return token;
    }

    public Boolean validateRegistration(String email, String token) {
        Optional<AuthToken> optionalAuthToken = authTokenRepository.findValidTokenByEmail(email);

        if (optionalAuthToken.isPresent()) {
            AuthToken authToken = optionalAuthToken.get();

            if (validateToken(authToken, token)) {
                return true;
            } else {
                throw new InvalidAuthTokenException();
            }
        }
        return false;
    }


    private Boolean validateToken(AuthToken authToken, String token) {

        return authToken.getToken().equals(generateHash(token));
    }


    public Optional<AuthToken> getAuthToken(String email) {

        return authTokenRepository.findValidTokenByEmail(email);

    }


    public Boolean thereIsAValidaToken(String email) {

        return authTokenRepository.findValidTokenByEmail(email).isPresent();
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }


    private String generateHash(String token) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
        byte[] hashBytes = md.digest(token.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void invalidateToken(String email) {
        Optional<AuthToken> optionalAuthToken = authTokenRepository.findValidTokenByEmail(email);

        optionalAuthToken.ifPresent(authToken -> {
            authToken.setUsed(true);
            authTokenRepository.save(authToken);
        });
    }
}


