package com.api.meusindicobrasil.service.impl;

import com.api.meusindicobrasil.dto.FuncionarioRecordDto;
import com.api.meusindicobrasil.entity.Funcionario;
import com.api.meusindicobrasil.enums.Atividade;
import com.api.meusindicobrasil.exception.NotFoundException;
import com.api.meusindicobrasil.exception.RegraDeNegocioException;
import com.api.meusindicobrasil.repository.FuncionarioRepository;
import com.api.meusindicobrasil.service.FuncionarioService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public Funcionario cadastrar(FuncionarioRecordDto dto) {

        Funcionario funcionario = dto.toEntity();

        validarEmailUnico(dto.email(), null);
        validarRgUnico(dto.rg(), null);

        return funcionarioRepository.save(funcionario);

    }

    @Override
    public Funcionario funcionarioPorId(UUID funcionarioId) {

        Funcionario funcionario = funcionarioRepository
                .findById(funcionarioId).orElseThrow(()-> new NotFoundException("ID não encontado"));

        return funcionario;
    }

    @Override
    public Map<String, Object> listar(int page, int size, String sortBy, String nome, String email, String telefoneContato, Atividade atividade) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.ASC, sortBy));

        Specification<Funcionario> spec =  (root, query, cb) -> cb.conjunction();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.like(
                                    cb.lower(root.get("nome")),
                                    "%" + nome.trim().toLowerCase() + "%"
                            )
            );
        }

        if (email != null && !email.isBlank()) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.like(
                                    cb.lower(root.get("email")),
                                    "%" + email.trim().toLowerCase() + "%"
                            )
            );
        }

        if (telefoneContato != null && !telefoneContato.isBlank()) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.like(
                                    cb.lower(root.get("email")),
                                    "%" + telefoneContato.trim().toLowerCase() + "%"
                            )
            );
        }

        if (atividade != null) {
            spec = spec.and(
                    (root, query, cb) ->
                            cb.equal(root.get("atividade"), atividade)
            );
        }

        Page<Funcionario> funcionarios = funcionarioRepository.findAll(spec, pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("response", funcionarios.getContent());
        response.put("total", funcionarios.getTotalElements());

        return response;
    }

    @Override
    public Funcionario atualizar(FuncionarioRecordDto dto, UUID funcionarioId) {

        Funcionario funcionario = dto.toEntity();

        validarEmailUnico(dto.email(), funcionarioId);
        validarRgUnico(dto.rg(), funcionarioId);

        funcionario.setNome(dto.nome());
        funcionario.setEmail(dto.email());
        funcionario.setRg(dto.rg());
        funcionario.setAtividade(dto.atividade());
        funcionario.setTelefoneContato(dto.telefoneContato());


        return funcionarioRepository.save(funcionario);
    }

    private void validarEmailUnico(String email, UUID funcionarioId) {

        funcionarioRepository.findByEmail(email)
                .ifPresent(funcionario -> {

                    if (funcionarioId == null ||
                            !funcionario.getId().equals(funcionarioId)) {

                        throw new RegraDeNegocioException(
                                "Já existe um funcionário cadastrado com o e-mail: " + email
                        );
                    }
                });
    }

    private void validarRgUnico(String rg, UUID funcionarioId) {

        funcionarioRepository.findByRg(rg)
                .ifPresent(funcionario -> {

                    if (funcionarioId == null ||
                            !funcionario.getId().equals(funcionarioId)) {

                        throw new RegraDeNegocioException(
                                "Já existe um funcionário cadastrado com o RG: " + rg
                        );
                    }
                });
    }
}
