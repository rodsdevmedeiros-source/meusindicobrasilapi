package com.api.meusindicobrasil.service;

import com.api.meusindicobrasil.dto.UsuarioCadastroRequestDTO;
import com.api.meusindicobrasil.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO cadastrar(UsuarioCadastroRequestDTO dto);
}
