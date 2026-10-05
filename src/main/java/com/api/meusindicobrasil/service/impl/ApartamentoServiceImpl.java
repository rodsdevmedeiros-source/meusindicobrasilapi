package com.api.meusindicobrasil.service.impl;

import com.api.meusindicobrasil.dto.ApartamentoRequestDto;
import com.api.meusindicobrasil.entity.Apartamento;
import com.api.meusindicobrasil.enums.Andar;
import com.api.meusindicobrasil.enums.Bloco;
import com.api.meusindicobrasil.exception.NotFoundException;
import com.api.meusindicobrasil.repository.ApartamentoRepository;
import com.api.meusindicobrasil.service.ApartamentoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


import java.util.HashMap;
import java.util.UUID;


@Service
@Transactional
public class ApartamentoServiceImpl implements ApartamentoService {

    private final ApartamentoRepository apartamentoRepository;

    public ApartamentoServiceImpl(ApartamentoRepository apartamentoRepository) {
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    public Apartamento cadastrar(ApartamentoRequestDto dto) {

        Apartamento apartamento = dto.toEntity();

        Apartamento salvo = apartamentoRepository.save(apartamento);

        return salvo;
    }

    @Override
    public Map<String, Object> listar(
            int page,
            int size,
            String sortBy,
            Integer numero,
            Andar andar,
            Bloco bloco,
            Integer qtdVagasGaragem,
            Integer qtdQuartos
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, sortBy)
        );

        Specification<Apartamento> spec =
                (root, query, cb) -> cb.conjunction();

        if (numero != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(root.get("numero"), numero)
            );
        }

        if (andar != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(root.get("andar"), andar)
            );
        }

        if (bloco != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(root.get("bloco"), bloco)
            );
        }

        if (qtdVagasGaragem != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("qtdVagasGaragem"),
                                    qtdVagasGaragem
                            )
            );
        }

        if (qtdQuartos != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(
                                    root.get("qtdQuartos"),
                                    qtdQuartos
                            )
            );
        }

        Page<Apartamento> apartamentos =
                apartamentoRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("list", apartamentos.getContent());
        response.put("x-total", apartamentos.getTotalElements());

        return response;
    }

    @Override
    public void deletar(UUID apartamentoId) {

        Apartamento apartamento = apartamentoPorId(apartamentoId);

        apartamentoRepository.delete(apartamento);
    }

    @Override
    public Apartamento editar(
            ApartamentoRequestDto dto,
            UUID apartamentoId
    ) {

        Apartamento apartamento = apartamentoPorId(apartamentoId);

        apartamento.setQtdVagasGaragem(dto.qtdVagasGaragem());
        apartamento.setAndar(dto.andar());
        apartamento.setNumero(dto.numero());
        apartamento.setBloco(dto.bloco());
        apartamento.setQtdQuartos(dto.qtdQuartos());
        apartamento.setQtdSalas(dto.qtdSalas());
        apartamento.setQtdSuites(dto.qtdSuites());

        return apartamentoRepository.save(apartamento);
    }

    @Override
    public Apartamento apartamentoPorId(UUID apartamentoId) {

        Apartamento apartamento = apartamentoRepository.findById(apartamentoId)
                .orElseThrow(()-> new NotFoundException("Id não encontrado: " + apartamentoId));

        return apartamento;
    }
}
