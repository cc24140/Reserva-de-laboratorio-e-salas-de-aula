package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="aluno")
@Data
@Getter
@Setter
@ToString
public class Aluno
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ra", length = 5)
    private String ra;

    @Column(name="nome", length = 80)
    private String nome;

    @Column(name="email", length = 80)
    private String email;

    public void setRA(String r)
    {  ra = r; }

    public void setNome(String n)
    {  nome = n; }

    public void setEmail(String e) {
        email = e;
    }

    public String getRa()
    {  return ra; }

    public String getNome()
    {  return nome; }

    public int getId()
    {  return id;   }

}
