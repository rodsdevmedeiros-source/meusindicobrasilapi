package com.api.meusindicobrasil.repository;

import com.api.meusindicobrasil.entity.Apartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ApartamentoRepository extends JpaRepository<Apartamento, UUID>, JpaSpecificationExecutor<Apartamento> {
}
