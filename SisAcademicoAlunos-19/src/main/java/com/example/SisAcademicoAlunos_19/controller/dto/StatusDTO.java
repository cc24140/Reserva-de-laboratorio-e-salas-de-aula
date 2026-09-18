package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusDTO(        // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        Long id,

        @NotBlank(message = "Código do status não pode estar em branco")
        String codigo,

        @NotBlank(message = "Nome do status não pode estar em branco")
        String nome
) {}
