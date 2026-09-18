package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LaboratorioDTO(   // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        Long id,

        @NotBlank(message = "Código do laboratório não pode estar em branco")
        String codigo,

        @NotBlank(message = "Nome do laboratório não pode estar em branco")
        String nome,

        @NotNull(message = "Capacidade é obrigatória")
        @Min(value = 1, message = "Capacidade deve ser maior que zero")
        Integer capacidade,

        @NotBlank(message = "Localização não pode estar em branco")
        String localizacao
) {}
