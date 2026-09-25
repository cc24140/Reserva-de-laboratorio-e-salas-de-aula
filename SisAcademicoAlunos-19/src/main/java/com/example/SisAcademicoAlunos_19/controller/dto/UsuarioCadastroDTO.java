package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioCadastroDTO(       // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        @NotBlank(message = "Campo obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF inválido")
        String cpf,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 10, max = 80, message = "Quantidade de caracteres incorreta!")
        String nome,

        @NotNull(message = "Campo obrigatório")
        LocalDate dataAniversario,

        @NotBlank(message = "Campo obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "Celular inválido")
        String celular,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 15, max = 80, message = "Quantidade de caracteres incorreta!")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Campo obrigatório")
        String login,

        @NotBlank(message = "Campo obrigatório")
        String senha
) {}
