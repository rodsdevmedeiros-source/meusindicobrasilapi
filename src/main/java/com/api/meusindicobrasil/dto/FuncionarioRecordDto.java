package com.api.meusindicobrasil.dto;

import com.api.meusindicobrasil.entity.Funcionario;
import com.api.meusindicobrasil.enums.Atividade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FuncionarioRecordDto(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(
                min = 3,
                max = 150,
                message = "O nome deve possuir entre 3 e 150 caracteres."
        )
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Informe um e-mail válido.")
        @Size(
                max = 150,
                message = "O e-mail deve possuir no máximo 150 caracteres."
        )
        String email,

        @NotBlank(message = "O RG é obrigatório.")
        @Size(
                min = 5,
                max = 20,
                message = "O RG deve possuir entre 5 e 20 caracteres."
        )
        String rg,

        @NotBlank(message = "O telefone de contato é obrigatório.")
        @Size(
                min = 8,
                max = 20,
                message = "O telefone deve possuir entre 8 e 20 caracteres."
        )
        String telefoneContato,

        @NotNull(message = "A atividade é obrigatória.")
        Atividade atividade

) {

    public Funcionario toEntity() {

        return Funcionario.builder()
                .nome(nome.trim())
                .email(email.trim().toLowerCase())
                .rg(rg.trim())
                .telefoneContato(telefoneContato.trim())
                .atividade(atividade)
                .build();
    }
}