package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "salas")
@Data
public class Sala { // representa a tabela do banco

    // id sempre
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // campos de cadastro da sala
    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "Campo obrigatório")
    private String codigo;

    @Column(nullable = false, length = 80)
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 10, max = 80, message = "Quantidade de caracteres incorreta!")
    private String nome;
    
    @Column(nullable = false)
    @NotNull(message = "Campo obrigatório")
    @Min(value = 1, message = "Valor fora do escopo")
    @Max(value = 40, message = "Valor fora do escopo")
    private Integer capacidade;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 50, message = "Quantidade de caracteres incorreta!")
    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
