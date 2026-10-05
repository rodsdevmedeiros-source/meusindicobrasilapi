package com.api.meusindicobrasil.dto;

import com.api.meusindicobrasil.entity.Usuario;
import com.api.meusindicobrasil.enums.Role;

import java.util.UUID;

public record UsuarioResponseDTO(

        UUID id,
        String nome,
        String email,
        Role role

) {

    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}
