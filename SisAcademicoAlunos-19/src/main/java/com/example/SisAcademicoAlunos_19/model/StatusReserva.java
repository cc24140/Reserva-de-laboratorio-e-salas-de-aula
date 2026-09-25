package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "status_reservas")
@Data
public class StatusReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "Campo obrigatório")
    private String codigo;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 50, message = "Quantidade de caracteres incorreta!")
    private String nome;
}
