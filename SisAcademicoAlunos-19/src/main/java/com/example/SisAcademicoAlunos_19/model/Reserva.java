package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "Data inicial é obrigatória")
    private LocalDate dataInicio;
    
    @Column(name = "data_fim", nullable = false)
    @NotNull(message = "Data final é obrigatória")
    private LocalDate dataFim;
    
    @Column(name = "hora_inicio", nullable = false)
    @NotNull(message = "Hora inicial é obrigatória")
    private LocalTime horaInicio;
    
    @Column(name = "hora_fim", nullable = false)
    @NotNull(message = "Hora final é obrigatória")
    private LocalTime horaFim;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "Usuário é obrigatório")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;
    
    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;
    
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private Status status;
}
