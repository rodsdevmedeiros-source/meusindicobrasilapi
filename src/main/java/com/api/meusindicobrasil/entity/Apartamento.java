package com.api.meusindicobrasil.entity;

import com.api.meusindicobrasil.enums.Andar;
import com.api.meusindicobrasil.enums.Bloco;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_apartamento")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Apartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "qtd_vagas_garagem", nullable = false)
    private Integer qtdVagasGaragem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Andar andar;

    @Column(nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    private Bloco bloco;

    @Column(name = "qtd_quartos")
    private Integer qtdQuartos;

    @Column(name = "qtd_salas")
    private Integer qtdSalas;

    @Column(name = "qtd_suites")
    private Integer qtdSuites;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}