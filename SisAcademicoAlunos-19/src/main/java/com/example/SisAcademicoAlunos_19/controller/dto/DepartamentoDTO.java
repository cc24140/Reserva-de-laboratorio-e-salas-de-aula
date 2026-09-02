package com.example.SisAcademicoAlunos_19.controller.dto;

import com.example.SisAcademicoAlunos_19.model.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record DepartamentoDTO(
        Integer id,
        @NotBlank(message = "Campo Obrigatório")
        @Size(min=10, max=100, message = "Campo fora do tamanho permitido")
        String nome,
        @NotNull(message = "Campo Obrigatório")
        @Size(min=2, max=30, message = "Campo fora do tamanho permitido")
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