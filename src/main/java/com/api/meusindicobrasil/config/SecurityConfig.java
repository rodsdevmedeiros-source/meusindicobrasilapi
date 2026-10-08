package com.api.meusindicobrasil.config;

import com.api.meusindicobrasil.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Rotas públicas
                        .requestMatchers(
                                "/auth/cadastro",
                                "/auth/login"
                        ).permitAll()

                        // Cadastrar apartamento
                        .requestMatchers(
                                HttpMethod.POST,
                                "/apartamento"
                        ).hasRole("USUARIO")

                        // Editar apartamento
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/apartamento/**"
                        ).hasRole("USUARIO")

                        // Deletar apartamento
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/apartamento/**"
                        ).hasRole("USUARIO")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/funcionario"
                        ).hasRole("USUARIO")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/funcionario",
                                "/funcionario/**"
                        ).authenticated()

                        // Consultar apartamentos
                        .requestMatchers(
                                HttpMethod.GET,
                                "/apartamento",
                                "/apartamento/**"
                        ).authenticated()

                        // Qualquer outra rota
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}