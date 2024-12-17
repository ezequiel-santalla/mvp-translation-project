package com.mvp.mvp_translation_project.services;

import com.mvp.mvp_translation_project.types.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
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
                    .signWith(SignatureAlgorithm.HS512, getSigningKey())  // Algoritmo de firma y clave
                    .compact();
        }

        public String getUsernameFromToken(String token) {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }

        public boolean validateToken(String token, UserDetails userDetails) {
            String username = getUsernameFromToken(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }
        private boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        private Date extractExpiration(String token) {
            return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody().getExpiration();
        }

        private byte[] getSigningKey() {
            return secretKey.getBytes(); // Devuelve la clave secreta en bytes
        }
    }
