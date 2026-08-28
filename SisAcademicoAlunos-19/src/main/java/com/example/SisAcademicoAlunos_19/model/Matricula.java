package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;

@Entity
@Table(name="matricula")
@Data
@Getter
@Setter
@ToString
public class Matricula
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="codigo", length = 5)
    private String codigo;

    @Column(name="data")
    private String data;

    @Column(name="hora")
    private Time hora;

    /*
    @OneToOne = uma matricula está vinculada somente a 1 aluno
    a classe MATRICULA possui a notation para referenciar o ALUNO
    ou seja, a MATRICULA está associada a 1 ALUNO
    @OneToOne
    */
    @OneToOne
         (fetch = FetchType.EAGER)
    @JoinColumn(name="idAluno")
    private Aluno aluno;

    /* @ManyToOne = várias matriculas para 1 curso
    o CURSO pode ter 1 ou várias MATRICULAS
    o MANY refere-se a MATRICULA
    TOONE refere-se ao CURSO
    */
    @ManyToOne
         (fetch = FetchType.EAGER)
    @JoinColumn(name="idCurso")
    private Curso curso;
    /* muitos registros da MATRICULA podem estar associados com
    1 CURSO através da coluna IDCURSO (FK) que será incluída na tabela
    */

    public void setCurso(Curso c) {
        curso  = c;
    }

    public void setCodigo(String c) {
        codigo = c;
    }

    public void setHora(Time t)
    {   hora = t; }

    public void setData(String d)
    {  data = d;  }

    public void setAluno(Aluno a)
    { aluno = a; }

    public String getCodigo()
    {  return codigo;   }

    public String getData()
    {  return data;  }

    public Time getHora()
    {  return hora;  }

    public Curso getCurso()
    {  return curso; }

    public Aluno getAluno()
    {  return aluno; }


}
