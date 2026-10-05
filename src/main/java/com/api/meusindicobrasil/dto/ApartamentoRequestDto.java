package com.api.meusindicobrasil.dto;

import com.api.meusindicobrasil.entity.Apartamento;
import com.api.meusindicobrasil.enums.Andar;
import com.api.meusindicobrasil.enums.Bloco;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ApartamentoRequestDto(

        @NotNull(message = "A quantidade de vagas de garagem é obrigatória.")
        @PositiveOrZero(message = "A quantidade de vagas de garagem não pode ser negativa.")
        Integer qtdVagasGaragem,

        @NotNull(message = "O andar é obrigatório.")
        Andar andar,

        @NotNull(message = "O número do apartamento é obrigatório.")
        @Positive(message = "O número do apartamento deve ser maior que zero.")
        Integer numero,

        @NotNull(message = "O bloco é obrigatório.")
        Bloco bloco,

        @NotNull(message = "A quantidade de quartos é obrigatória.")
        @PositiveOrZero(message = "A quantidade de quartos não pode ser negativa.")
        Integer qtdQuartos,

        @NotNull(message = "A quantidade de salas é obrigatória.")
        @PositiveOrZero(message = "A quantidade de salas não pode ser negativa.")
        Integer qtdSalas,

        @NotNull(message = "A quantidade de suítes é obrigatória.")
        @PositiveOrZero(message = "A quantidade de suítes não pode ser negativa.")
        Integer qtdSuites

) {

    public Apartamento toEntity() {
        return Apartamento.builder()
                .qtdVagasGaragem(qtdVagasGaragem)
                .andar(andar)
                .numero(numero)
                .bloco(bloco)
                .qtdQuartos(qtdQuartos)
                .qtdSalas(qtdSalas)
                .qtdSuites(qtdSuites)
                .build();
    }
}