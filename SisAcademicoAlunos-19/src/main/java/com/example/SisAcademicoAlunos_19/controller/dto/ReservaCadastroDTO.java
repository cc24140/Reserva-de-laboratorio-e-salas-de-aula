package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaCadastroDTO(       // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        @NotNull(message = "Campo obrigatório")
        LocalDate dataInicio,

        @NotNull(message = "Campo obrigatório")
        LocalDate dataFim,

        @NotNull(message = "Campo obrigatório")
        LocalTime horaInicio,

        @NotNull(message = "Campo obrigatório")
        LocalTime horaFim,

        @NotNull(message = "Campo obrigatório")
        Long usuarioId,

        Long laboratorioId,

        Long salaId,

        Long statusReservaId,

        Long statusId
) {}
