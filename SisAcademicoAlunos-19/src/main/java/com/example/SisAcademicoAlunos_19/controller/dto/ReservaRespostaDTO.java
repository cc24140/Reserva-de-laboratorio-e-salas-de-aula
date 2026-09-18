package com.example.SisAcademicoAlunos_19.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaRespostaDTO(   // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        Long id,
        LocalDate dataInicio,
        LocalDate dataFim,
        LocalTime horaInicio,
        LocalTime horaFim,
        Long usuarioId,
        String nomeUsuario,
        Long laboratorioId,
        Long salaId,
        Long statusId,
        String nomeStatus
) {}
