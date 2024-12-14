package com.mvp.mvp_translation_project.configs;

import com.mvp.mvp_translation_project.services.CustomUserDetailsService;
import com.mvp.mvp_translation_project.types.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





//Usar esta version de filterChain para no tener que autenticar ningun endopint

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()) // Permitir todas las solicitudes
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // Para H2-Console

        return http.build();
    }


/*
    //Usar esta version de filterChain para usar autenticacion basada en roles


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Endpoints con acceso basado en roles
                        .requestMatchers("/admin/**", "/projects/**", "/auth-token/**").hasAnyRole("ADMIN", "ROOT")
                        .requestMatchers("/user/**").hasAnyRole("TRANSLATOR", "ADMIN", "ROOT")
                        // Cualquier otra solicitud debe estar autenticada
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Autenticación HTTP Basic

        return http.build();
    }
    */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
