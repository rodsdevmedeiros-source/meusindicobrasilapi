package com.api.meusindicobrasil.security;

import com.api.meusindicobrasil.entity.Usuario;
import com.api.meusindicobrasil.repository.UsuarioRepository;
import com.api.meusindicobrasil.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        if (!jwtService.tokenValido(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extrairEmail(token);

        if (email != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            Usuario usuario = usuarioRepository
                    .findByEmail(email)
                    .orElse(null);

            if (usuario != null && Boolean.TRUE.equals(usuario.getAtivo())) {

                var authorities = List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + usuario.getRole().name()
                        )
                );

                var authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                authorities
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
