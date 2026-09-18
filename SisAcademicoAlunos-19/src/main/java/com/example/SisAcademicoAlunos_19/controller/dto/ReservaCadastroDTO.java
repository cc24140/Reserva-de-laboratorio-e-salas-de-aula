package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaCadastroDTO(       // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        @NotNull(message = "Data inicial é obrigatória")
        LocalDate dataInicio,

        @NotNull(message = "Data final é obrigatória")
        LocalDate dataFim,

        @NotNull(message = "Hora inicial é obrigatória")
        LocalTime horaInicio,

        @NotNull(message = "Hora final é obrigatória")
        LocalTime horaFim,

        @NotNull(message = "Usuário é obrigatório")
        Long usuarioId,

        Long laboratorioId,

        Long salaId,

        @NotNull(message = "Status é obrigatório")
        Long statusId
) {}
