package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "statuses")
@Data
public class Status {

    // id sempre
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // campos de cadastro do status
    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "Código não pode estar em branco")
    private String codigo;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;
}
