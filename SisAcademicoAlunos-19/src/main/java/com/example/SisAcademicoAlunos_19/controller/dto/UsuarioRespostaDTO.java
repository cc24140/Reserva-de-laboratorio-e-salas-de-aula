package com.example.SisAcademicoAlunos_19.controller.dto;

import java.time.LocalDate;

public record UsuarioRespostaDTO(   // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        Long id,
        String cpf,
        String nome,
        LocalDate dataAniversario,
        String celular,
        String email,
        String login
) {}
