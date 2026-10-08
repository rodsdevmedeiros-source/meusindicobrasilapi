package com.api.meusindicobrasil.controller;

import com.api.meusindicobrasil.dto.FuncionarioRecordDto;
import com.api.meusindicobrasil.entity.Funcionario;
import com.api.meusindicobrasil.enums.Andar;
import com.api.meusindicobrasil.enums.Atividade;
import com.api.meusindicobrasil.enums.Bloco;
import com.api.meusindicobrasil.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(@Valid @RequestBody FuncionarioRecordDto dto){

        Funcionario funcionario = funcionarioService.cadastrar(dto);

        return new  ResponseEntity<>(funcionario,HttpStatus.CREATED);

    }

    @GetMapping("/{funcionarioId}")
    public ResponseEntity<Funcionario> funcionarioPorId(@PathVariable UUID funcionarioId){

        Funcionario funcionario = funcionarioService.funcionarioPorId(funcionarioId);

        return new ResponseEntity<>(funcionario, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numero") String sortBy,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) Atividade atividade

    ){
        Map<String, Object> response = funcionarioService.listar(page, size, sortBy, nome, email, telefone, atividade);

        List<Funcionario> lista = (List<Funcionario>) response.get("response");

        Long total = (Long) response.get("x-total");

        return ResponseEntity.ok()
                .header("X-Total", String.valueOf(total))
                .body(lista);
    }

    @PutMapping("/{funcionarioID}")
    public ResponseEntity<Funcionario> atualizar(
            @Valid @RequestBody FuncionarioRecordDto dto,
            @PathVariable UUID funcionarioID){

        Funcionario funcionario = funcionarioService.atualizar(dto, funcionarioID);

        return new ResponseEntity<>(funcionario, HttpStatus.OK);

    }
}
