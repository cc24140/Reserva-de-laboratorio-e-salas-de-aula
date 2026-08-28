package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Entity
@Table(name="departamento")
@Data
public class Departamento
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nome", length = 80)
    private String nome;

    @Column(name="localizacao", length = 30)
    private String localizacao;

    @Column(name="faturamento")
    private Double faturamento;

    @Column(name ="responsavel",length = 50)
    private String responsavel;

    public void setNome(String n)
    {   nome = n; }

    public void setLocalizacao(String l)
    {   localizacao = l; }

    public void setFaturamento(Double f)
    {   faturamento = f; }

    public void setResponsavel(String r)
    {   responsavel = r; }

    public String getNome()
    {   return nome;  }

    public Integer getId()
    {   return id;  }

    public String getLocalizacao()
    {   return localizacao;  }

    public Double getFaturamento()
    {   return faturamento;  }

    public String getResponsavel()
    {   return responsavel;  }


}
