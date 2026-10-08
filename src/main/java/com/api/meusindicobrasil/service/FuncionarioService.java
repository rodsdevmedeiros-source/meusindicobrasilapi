package com.api.meusindicobrasil.service;

import com.api.meusindicobrasil.dto.FuncionarioRecordDto;
import com.api.meusindicobrasil.entity.Funcionario;
import com.api.meusindicobrasil.enums.Atividade;

import java.util.Map;
import java.util.UUID;

public interface FuncionarioService {

    Funcionario cadastrar(FuncionarioRecordDto dto);

    Funcionario funcionarioPorId(UUID funcionarioId);

    Map<String, Object> listar(
            int page,
            int size,
            String sortBy,
            String nome,
            String email,
            String telefoneContato,
            Atividade atividade
    );

    Funcionario atualizar(FuncionarioRecordDto dto, UUID funcionarioId);
}
