package com.api.meusindicobrasil.service;

import com.api.meusindicobrasil.dto.ApartamentoRequestDto;
import com.api.meusindicobrasil.entity.Apartamento;
import com.api.meusindicobrasil.enums.Andar;
import com.api.meusindicobrasil.enums.Bloco;

import java.util.Map;
import java.util.UUID;

public interface ApartamentoService {

    Apartamento cadastrar(ApartamentoRequestDto dto);

    Map<String, Object> listar(int page, int size, String sortBy, Integer numero, Andar andar, Bloco bloco, Integer qtdVagasGaragem,Integer qtdQuartos);

    Apartamento editar(ApartamentoRequestDto dto, UUID apartamentoId);

    Apartamento apartamentoPorId(UUID apartamentoId);

    void deletar(UUID apartamentoId);
}
