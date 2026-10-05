package com.api.meusindicobrasil.dto;

import com.api.meusindicobrasil.enums.Role;

import java.util.UUID;

public record LoginResponseDTO(

        String token,
        UUID usuarioId,
        String nome,
        String email,
        Role role

) {
}