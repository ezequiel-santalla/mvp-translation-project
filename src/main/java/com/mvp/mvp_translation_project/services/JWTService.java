package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.types.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

    @Service
    public class JWTService {

        @Value("${jwt.secret}")
        private String secretKey;  // Clave secreta desde el archivo de configuración

        @Value("${jwt.expiration}")
        private long expirationTime;  // Tiempo de expiración desde el archivo de configuración

        public String generateJwtToken(String username, RoleType role) {
            // Construir y devolver el token
            return Jwts.builder()
                    .setSubject(username)  // Identidad del usuario
                    .claim("role", role)  // Agregar rol al token
                    .setIssuedAt(new Date())  // Fecha de emisión
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Fecha de expiración
                    .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())  // Algoritmo de firma y clave
                    .compact();
        }

        public String getUsernameFromToken(String token) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                // Manejar la excepción (token inválido, expirado, etc.)
                return false;
            }
        }
    }
