package com.example.SisAcademicoAlunos_19.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Data
public class Reserva {  // representa a tabela do banco

    // id sempre
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // campos de cadastro da reserva
    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "Campo obrigatório")
    private LocalDate dataInicio;
    
    @Column(name = "data_fim", nullable = false)
    @NotNull(message = "Campo obrigatório")
    private LocalDate dataFim;
    
    @Column(name = "hora_inicio", nullable = false)
    @NotNull(message = "Campo obrigatório")
    private LocalTime horaInicio;
    
    @Column(name = "hora_fim", nullable = false)
    @NotNull(message = "Campo obrigatório")
    private LocalTime horaFim;

    // relacionamento com outras entidades
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "Campo obrigatório")
    private Usuario usuario;

    // codigo do recurso (laboratório ou sala) que está sendo reservado
    @ManyToOne
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;
    // ou
    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;
    
    @ManyToOne
    @JoinColumn(name = "status_reserva_id", nullable = false)
    @NotNull(message = "Campo obrigatório")
    private StatusReserva statusReserva;

    @Deprecated
    @Transient
    public Status getStatus() {
        if (this.statusReserva == null) {
            return null;
        }
        Status status = new Status();
        status.setId(this.statusReserva.getId());
        status.setCodigo(this.statusReserva.getCodigo());
        status.setNome(this.statusReserva.getNome());
        return status;
    }

    @Deprecated
    public void setStatus(Status status) {
        if (status == null) {
            this.statusReserva = null;
            return;
        }
        StatusReserva statusReserva = new StatusReserva();
        statusReserva.setId(status.getId());
        statusReserva.setCodigo(status.getCodigo());
        statusReserva.setNome(status.getNome());
        this.statusReserva = statusReserva;
    }
}
