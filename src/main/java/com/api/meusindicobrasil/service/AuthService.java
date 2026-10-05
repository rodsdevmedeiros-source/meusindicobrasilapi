package com.api.meusindicobrasil.service;

import com.api.meusindicobrasil.dto.LoginRequestDTO;
import com.api.meusindicobrasil.dto.LoginResponseDTO;
import com.api.meusindicobrasil.entity.Usuario;
import com.api.meusindicobrasil.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        String email = dto.email()
                .trim()
                .toLowerCase();

        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("E-mail ou senha inválidos.")
                );

        if (!usuario.getAtivo()) {
            throw new RuntimeException("Usuário inativo.");
        }

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("E-mail ou senha inválidos.");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponseDTO(
                token,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}
