package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="disciplina")
@Data
@Getter
@Setter
@ToString
public class Disciplina
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="codigo", length = 5, nullable = false)
    private String codigo;

    @Column(name="nome", length = 50, nullable = false)
    private String nome;

    @Column(name="cargaHoraria", nullable = false)
    private Double cargaHoraria;

    @Column(name="qtdeAulas", nullable = false)
    private Integer qtdeAulas;

    // Muitas DISCIPLINAS para 1 CURSO
    // Many refere-se a DISCIPLINA
    // ToOne refere-se a CURSO
    // o CURSO pode ter 1 ou mais DISCIPLINAS
   @ManyToOne
    ( fetch = FetchType.EAGER  )
    @JoinColumn(name="idCurso")
    private Curso curso;

   // @ManyToMany = várias disciplinas para vários professores
   // a DISCIPLINA pode ter 1 ou vários PROFESSORES
   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(
           name="disciplinaProfessor",
           joinColumns = @JoinColumn(name="idDisciplina"),
           inverseJoinColumns = @JoinColumn(name="idProfessor"))
   @ToString.Exclude
   @Transient
   private List<Disciplina> disciplinas;

   @OneToMany
   private List<Avaliacao> avaliacoes;


    public void setCodigo(String c)
    {  codigo = c; }

    public void setNome(String n)
    {  nome = n;  }

    public void setCargaHoraria(double c)
    { cargaHoraria = c;  }

    public void setQtdeAulas(int q)
    {   qtdeAulas = q;  }

    public void setCurso(Curso c)
    {   curso = c;  }

    public String getCodigo()
    {  return codigo; }

    public String getNome()
    {  return nome;   }

    public Double getCargaHoraria()
    {  return cargaHoraria;   }

    public Integer getQtdeAulas()
    {  return qtdeAulas;   }

    public String getCurso()
    {  return curso.getNome();  }

    public List<Avaliacao> getAvaliacoes()
    { return avaliacoes;  }

}
