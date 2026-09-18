package com.example.SisAcademicoAlunos_19.controller.dto;

public record LoginRespostaDTO( // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        Long usuarioId,
        String nome,
        String login,
        String mensagem
) {}
