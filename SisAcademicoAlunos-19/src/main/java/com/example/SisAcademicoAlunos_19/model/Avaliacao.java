package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="avaliacao")
@Data
@Getter
@Setter
@ToString
public class Avaliacao
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="codigo", length = 5)
    private String codigo;

    // mascara = 99/99/999912:23
    @Column(name="datahora", length = 15)
    private String datahora;

    @Column(name="nota")
    private Double nota;

    // @ManyToOne = várias avaliações para 1 professor
    // o PROFESSOR pode ter ou várias AVALIAÇÕES
    // o MANY refere-se a AVALICAO
    // TOONE refere-se ao PROFESSOR
    @ManyToOne
        ( fetch = FetchType.EAGER)
    @JoinColumn(name="idProfessor")
    private Professor professor;

    @ManyToOne
        ( fetch = FetchType.EAGER)
    @JoinColumn(name="idDisciplina")
    private Disciplina disciplina;

    @ManyToOne
        ( fetch = FetchType.EAGER)
    @JoinColumn(name="idAluno")
    private Aluno aluno;


    public void setCodigo(String c)
    {  codigo = c; }

    public void setNota(double n)
    {  nota = n;   }

    public void setDataHora(String d)
    {  datahora = d;  }

    public void setAluno(Aluno a)
    {  aluno = a;  }

    public void setProfessor(Professor p)
    {  professor = p;  }

    public void setDisciplina(Disciplina d)
    {  disciplina = d; }

    public Integer getId()
    {  return id; }

    public String getCodigo()
    {  return codigo; }

    public Double getNota()
    {  return nota; }

    public String getDatahora()
    {  return datahora; }

    public Aluno getAluno()
    {  return aluno;   }
}
