package com.example.SisAcademicoAlunos_19.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UsuarioCadastroDTO(       // é o formato que chega/sai da API, ajuda a não expor campos sensíveis como senha em retorno (se comunica com model)
        @NotBlank(message = "CPF não pode estar em branco")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotBlank(message = "Nome completo não pode estar em branco")
        String nome,

        @NotNull(message = "Data de aniversário é obrigatória")
        LocalDate dataAniversario,

        @NotBlank(message = "Celular não pode estar em branco")
        @Pattern(regexp = "\\d{10,11}", message = "Celular deve conter 10 ou 11 dígitos")
        String celular,

        @NotBlank(message = "Email não pode estar em branco")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Login não pode estar em branco")
        String login,

        @NotBlank(message = "Senha não pode estar em branco")
        String senha
) {}
