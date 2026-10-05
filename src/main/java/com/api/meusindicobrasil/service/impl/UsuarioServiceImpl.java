package com.api.meusindicobrasil.service.impl;

import com.api.meusindicobrasil.dto.UsuarioCadastroRequestDTO;
import com.api.meusindicobrasil.dto.UsuarioResponseDTO;
import com.api.meusindicobrasil.entity.Usuario;
import com.api.meusindicobrasil.enums.Role;
import com.api.meusindicobrasil.repository.UsuarioRepository;
import com.api.meusindicobrasil.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioCadastroRequestDTO dto) {

        String email = dto.email()
                .trim()
                .toLowerCase();

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(email)
                .senha(passwordEncoder.encode(dto.senha()))
                .role(Role.USUARIO)
                .ativo(true)
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioResponseDTO.fromEntity(usuarioSalvo);
    }
}
