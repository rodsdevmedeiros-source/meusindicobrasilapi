package com.api.meusindicobrasil.controller;


import com.api.meusindicobrasil.dto.LoginRequestDTO;
import com.api.meusindicobrasil.dto.LoginResponseDTO;
import com.api.meusindicobrasil.dto.UsuarioCadastroRequestDTO;
import com.api.meusindicobrasil.dto.UsuarioResponseDTO;
import com.api.meusindicobrasil.service.AuthService;
import com.api.meusindicobrasil.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    private  final AuthService authService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(
            @Valid @RequestBody UsuarioCadastroRequestDTO dto) {

        UsuarioResponseDTO usuario = usuarioService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(
                authService.login(dto)
        );
    }
}
