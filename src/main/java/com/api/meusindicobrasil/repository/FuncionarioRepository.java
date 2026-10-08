package com.api.meusindicobrasil.repository;

import com.api.meusindicobrasil.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID>, JpaSpecificationExecutor<Funcionario> {

    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByRg(String rg);

}
