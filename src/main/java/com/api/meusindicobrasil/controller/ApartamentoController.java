package com.api.meusindicobrasil.controller;

import com.api.meusindicobrasil.dto.ApartamentoRequestDto;
import com.api.meusindicobrasil.entity.Apartamento;
import com.api.meusindicobrasil.enums.Andar;
import com.api.meusindicobrasil.enums.Bloco;
import com.api.meusindicobrasil.service.ApartamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/apartamento")
public class ApartamentoController {

    private final ApartamentoService apartamentoService;

    public ApartamentoController(ApartamentoService apartamentoService) {
        this.apartamentoService = apartamentoService;
    }

    @PostMapping
    public ResponseEntity<Apartamento> cadastrar(@Valid @RequestBody ApartamentoRequestDto dto){
        Apartamento apartamento = apartamentoService.cadastrar(dto);
        return new ResponseEntity<>(apartamento, HttpStatus.CREATED);
    }

    @GetMapping("/{apartamentoId}")
    public ResponseEntity<Apartamento> apartamentoPorId(
            @PathVariable UUID apartamentoId) {

        Apartamento apartamento =
                apartamentoService.apartamentoPorId(apartamentoId);

        return ResponseEntity.ok(apartamento);
    }

    @GetMapping
    public ResponseEntity<List<Apartamento>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numero") String sortBy,
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) Andar andar,
            @RequestParam(required = false) Bloco bloco,
            @RequestParam(required = false) Integer qtdVagasGaragem,
            @RequestParam(required = false) Integer qtdQuartos
    ) {

        Map<String, Object> resultado = apartamentoService.listar(
                page,
                size,
                sortBy,
                numero,
                andar,
                bloco,
                qtdVagasGaragem,
                qtdQuartos
        );

        List<Apartamento> apartamentos =
                (List<Apartamento>) resultado.get("list");

        Long total =
                (Long) resultado.get("x-total");

        return ResponseEntity.ok()
                .header("X-Total", String.valueOf(total))
                .body(apartamentos);
    }

    @PutMapping("/{apartamentoId}")
    public ResponseEntity<Apartamento> editar(
            @PathVariable UUID apartamentoId,
            @Valid @RequestBody ApartamentoRequestDto dto
    ) {

        Apartamento apartamento =
                apartamentoService.editar(dto, apartamentoId);

        return ResponseEntity.ok(apartamento);
    }

    @DeleteMapping("/{apartamentoId}")
    public ResponseEntity<Void> deletar(
            @PathVariable UUID apartamentoId
    ) {

        apartamentoService.deletar(apartamentoId);

        return ResponseEntity.noContent().build();
    }
}
