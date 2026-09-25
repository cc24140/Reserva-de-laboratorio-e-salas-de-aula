package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SalaDTO(  // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        Long id,

        @NotBlank(message = "Campo obrigatório")
        String codigo,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 10, max = 80, message = "Quantidade de caracteres incorreta!")
        String nome,

        @NotNull(message = "Campo obrigatório")
        @Min(value = 1, message = "Valor fora do escopo")
        @Max(value = 40, message = "Valor fora do escopo")
        Integer capacidade,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 50, message = "Quantidade de caracteres incorreta!")
        String localizacao,

        Long statusId
) {}
