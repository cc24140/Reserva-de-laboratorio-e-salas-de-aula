package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "salas")
@Data
public class Sala {

    // id sempre
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // campos de cadastro da sala
    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "Código não pode estar em branco")
    private String codigo;
    
    @Column(nullable = false, length = 150)
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;
    
    @Column(nullable = false)
    @NotNull(message = "Capacidade é obrigatória")
    @Min(value = 1, message = "Capacidade deve ser maior que 0")
    private Integer capacidade;
    
    @Column(nullable = false, length = 200)
    @NotBlank(message = "Localização não pode estar em branco")
    private String localizacao;
}
