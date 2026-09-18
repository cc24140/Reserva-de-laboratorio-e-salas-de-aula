package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO( // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        @NotBlank(message = "Login não pode estar em branco")
        String login,

        @NotBlank(message = "Senha não pode estar em branco")
        String senha
) {}
