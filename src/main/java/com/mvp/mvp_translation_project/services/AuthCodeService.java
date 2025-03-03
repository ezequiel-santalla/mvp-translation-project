package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.exceptions.InvalidAuthTokenException;
import com.mvp.mvp_translation_project.exceptions.AuthTokenNotFoundException;
import com.mvp.mvp_translation_project.exceptions.UserAlreadyExistsException;
import com.mvp.mvp_translation_project.models.AuthCode;
import com.mvp.mvp_translation_project.repositories.AuthCodeRepository;
import com.mvp.mvp_translation_project.types.AuthCodeType;
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
public class AuthCodeService {

    private final AuthCodeRepository authCodeRepository;
    private final UserService userService;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer TOKEN_LENGTH = 6;

    @Autowired
    public AuthCodeService(AuthCodeRepository authCodeRepository, UserService userService) {
        this.authCodeRepository = authCodeRepository;
        this.userService = userService;
    }

    //Crea un token para recuperar la cuenta
    @Transactional
    public String createRecoveryCode(String email) {


        invalidateCode(email, AuthCodeType.RECOVERY);
        AuthCode recoveryCode = new AuthCode();
        String code = generateCode();

        recoveryCode.setEmail(email);
        recoveryCode.setCode(generateHash(code));
        recoveryCode.setExpiration(LocalDateTime.now().plusMinutes(20));
        recoveryCode.setCodeType(AuthCodeType.RECOVERY);
        recoveryCode.setUsed(false);
        authCodeRepository.save(recoveryCode);

        return code;
    }

    //Crea un token para permitir el registro de un nuevo usuario
    public String createPreRegistrationCode(String email) {

        if(Boolean.TRUE.equals(userService.existsUserByEmail(email))){
            throw new UserAlreadyExistsException(email);
        }
        invalidateCode(email, AuthCodeType.PRE_REGISTRATION);
        AuthCode preRegistrationCode = new AuthCode();
        String token = generateCode();

        preRegistrationCode.setEmail(email);
        preRegistrationCode.setCode(generateHash(token));
        preRegistrationCode.setExpiration(LocalDateTime.now().plusDays(2));
        preRegistrationCode.setCodeType(AuthCodeType.PRE_REGISTRATION);
        preRegistrationCode.setUsed(false);
        authCodeRepository.save(preRegistrationCode);
        return token;
    }


    public Boolean validateCode(String email, String code, AuthCodeType codeType) {
        Optional<AuthCode> optionalAuthToken = authCodeRepository
                .findValidCodeByEmailAndType(email, codeType);

        if (optionalAuthToken.isPresent()) {
            AuthCode authToken = optionalAuthToken.get();

            if (Boolean.TRUE.equals(isValidCode(authToken, code))) {
                invalidateCode(email, codeType);
                return true;
            } else {
                throw new InvalidAuthTokenException();
            }
        } else {
            throw new AuthTokenNotFoundException(email);
        }
    }

    public Boolean resetPassword (String email, String recoveryCode, String newPassword){

       if(Boolean.TRUE.equals(validateCode(email, recoveryCode, AuthCodeType.RECOVERY)) ){
           userService.hardResetPassword(email, newPassword);
       }
       return true;
    }



    private Boolean isValidCode(AuthCode authCode, String code) {
        return authCode.getCode().equals(generateHash(code));
    }


    public Optional<AuthCode> getAuthCode(String email, AuthCodeType codeType) {

        return authCodeRepository.findValidCodeByEmailAndType(email, codeType);

    }


    public Boolean thereIsAValidaCode(String email, AuthCodeType codeType) {

        return authCodeRepository.findValidCodeByEmailAndType(email, codeType).isPresent();
    }

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }


    private String generateHash(String code) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
        byte[] hashBytes = md.digest(code.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public void invalidateCode(String email, AuthCodeType codeType) {
        Optional<AuthCode> optionalAuthCode = authCodeRepository
                .findValidCodeByEmailAndType(email, codeType);

        optionalAuthCode.ifPresent(authToken -> {
            authToken.setUsed(true);
            authCodeRepository.save(authToken);
        });
    }

}


