package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StatusReservaDTO(
        Long id,

        @NotBlank(message = "Campo obrigatório")
        String codigo,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 50, message = "Quantidade de caracteres incorreta!")
        String nome
) {}
