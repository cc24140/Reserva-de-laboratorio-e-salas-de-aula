package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="curso")
// a notation @Data gera os getters/setters
// e construtor vazio
@Getter
@Setter
@ToString
@Data
public class Curso
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="codigo", length = 5, nullable = false)
    private String codigo;

    @Column(name="nome", length = 80, nullable = false)
    private String nome;

    @Column(name="cargaHoraria", nullable = false)
    private Double cargaHoraria;

    /* @ManyToOne = vários cursos para 1 departamento
    a classe CURSO possui a notation para referenciar o DEPARTAMENTO
    ou seja, o DEPARTAMENTO pode ter 1 ou vários CURSOS
    MANY refere-se a CURSO
    TOONE refere-se a DEPARTAMENTO
    */
    @ManyToOne
       // com o fetch EAGER todos os seus relacionamentos associados
       // também serão carregados no mesmo SELECT usando o JOIN
       (fetch = FetchType.EAGER)
    @JoinColumn(name="id_departamento")
    private Departamento departamento;

    @OneToMany
    private List<Curso> listaDeCursos;


    public void setCodigo(String c)
    {   codigo = c; }

    public void setNome(String n)
    {   nome = n;  }

    public void setCargaHoraria(double c)
    {   cargaHoraria = c; }

    public void setDepartamento(Departamento d)
    {   departamento = d;  }

    public String getCodigo()
    {   return codigo;  }

    public String getNome()
    {   return nome;  }

    public Double getCargaHoraria()
    {   return cargaHoraria;  }
}
