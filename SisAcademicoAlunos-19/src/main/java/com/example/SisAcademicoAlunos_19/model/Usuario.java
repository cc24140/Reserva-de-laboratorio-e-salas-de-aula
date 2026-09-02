package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 11)
    @NotBlank(message = "CPF não pode estar em branco")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    private String cpf;
    
    @Column(nullable = false, length = 150)
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;
    
    @Column(name = "data_aniversario")
    @NotNull(message = "Data de aniversário é obrigatória")
    private LocalDate dataAniversario;
    
    @Column(length = 15)
    @NotBlank(message = "Celular não pode estar em branco")
    @Pattern(regexp = "\\d{10,11}", message = "Celular deve conter 10 ou 11 dígitos")
    private String celular;
    
    @Column(unique = true, nullable = false, length = 120)
    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email deve ser válido")
    private String email;
    
    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "Login não pode estar em branco")
    private String login;
    
    @Column(name = "senha_hash", nullable = false)
    @NotBlank(message = "Senha não pode estar em branco")
    private String senhaHash;
}
