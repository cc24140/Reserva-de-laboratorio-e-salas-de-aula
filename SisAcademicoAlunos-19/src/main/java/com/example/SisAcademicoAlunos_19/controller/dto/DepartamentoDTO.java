package com.example.SisAcademicoAlunos_19.controller.dto;

import com.example.SisAcademicoAlunos_19.model.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartamentoDTO(
        Integer id,
        @NotBlank(message = "Campo Obrigatório")
        String nome,
        @NotNull(message = "Campo Obrigatório")
        String localizacao,
        @NotNull(message = "Campo Obrigatório")
        Double faturamento,
        @NotNull(message = "Campo Obrigatório")
        String responsavel
)
{
    // transformar o DTO de Departamento em um objeto Departamento
    public Departamento mapearDadosParaEntidadeDepartamento()
    {
        Departamento departamento = new Departamento();
        departamento.setNome(this.nome);
        departamento.setLocalizacao(this.localizacao);
        departamento.setFaturamento(this.faturamento);
        departamento.setResponsavel(this.responsavel);
        return departamento;
    }
}