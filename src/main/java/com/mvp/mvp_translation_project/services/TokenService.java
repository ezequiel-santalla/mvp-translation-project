package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.models.RevokedToken;
import com.mvp.mvp_translation_project.repositories.RevokedTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final RevokedTokenRepository revokedTokenRepository;

    public TokenService(RevokedTokenRepository revokedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    public void revokeToken(String token) {
        RevokedToken revokedToken = new RevokedToken(token);
        revokedTokenRepository.save(revokedToken);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.findByToken(token).isPresent();
    }
}
