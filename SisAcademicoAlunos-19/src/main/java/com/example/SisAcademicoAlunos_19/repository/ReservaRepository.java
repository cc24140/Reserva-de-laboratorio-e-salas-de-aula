package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuario_Id(Long usuarioId);
    List<Reserva> findByStatus_Id(Long statusId);
    List<Reserva> findByLaboratorio_Id(Long laboratorioId);
    List<Reserva> findBySala_Id(Long salaId);
    List<Reserva> findByDataInicioBetween(LocalDate inicio, LocalDate fim);
    List<Reserva> findByDataInicio(LocalDate data);

    // Busca reservas que se sobreponham a um intervalo para um recurso (laboratório ou sala)
    @Query("SELECT r FROM Reserva r WHERE ( (r.laboratorio.id = :laboratorioId AND :laboratorioId IS NOT NULL) OR (r.sala.id = :salaId AND :salaId IS NOT NULL) ) " +
           "AND r.dataInicio <= :endDate AND r.dataFim >= :startDate " +
           "AND r.horaInicio < :horaFim AND r.horaFim > :horaInicio")
    List<Reserva> findOverlappingReservations(@Param("laboratorioId") Long laboratorioId,
                                              @Param("salaId") Long salaId,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate,
                                              @Param("horaInicio") LocalTime horaInicio,
                                              @Param("horaFim") LocalTime horaFim);
}
