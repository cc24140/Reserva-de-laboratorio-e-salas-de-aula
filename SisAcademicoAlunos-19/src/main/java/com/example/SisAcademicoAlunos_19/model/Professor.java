package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="professor")
@Data
@Getter
@Setter
@ToString
public class Professor
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="matricula", length = 5)
    private String matricula;

    @Column(name="nome", length = 80)
    private String nome;

    @Column(name="email", length = 80)
    private String email;

    @Column(name="celular", length = 15)
    private String celular;

    @Column(name="dataNascimento")
    private Date dataNascimento;

    // @ManyToOne = vários professores para 1 departamento
    // o DEPARTAMENTO pode ter 1 ou vários PROFESSORES
    // o MANY refere-se ao PROFESSOR
    // TOONE refere-se ao DEPARTAMENTO
    @ManyToOne
        (fetch = FetchType.EAGER)
    @JoinColumn(name="idDepartamento")
    private Departamento departamento;

    // @ManytoMany = vários professores para várias disciplinas
    // várias disciplina para vários professores
    // o PROFESSOR pode ter 1 ou vários DISCIPLINAS
    // a DISCIPLINA pode ter 1 ou vários PROFESSORES
    @ManyToMany()
    private List<Disciplina> disciplinas;

    @OneToMany
    private List<Avaliacao> avaliacoes;


    public String getMatricula()
    {  return matricula;  }

    public String getNome()
    {  return nome;  }

    public void setNome(String n)
    {  nome = n;   }

    public void setEmail(String e)
    {  email = e;  }

    public void setCelular(String c)
    {  celular = c;  }

    public void setMatricula(String m)
    {  matricula = m;  }

    public void setDataNascimento(java.sql.Date d)
    {   dataNascimento = d; }

    public void setDepartamento(Departamento d)
    {  departamento = d;  }

    public List<Disciplina> getDisciplinas()
    {   return disciplinas;  }

    public List<Avaliacao> getAvaliacoes()
    {  return avaliacoes;   }

}
