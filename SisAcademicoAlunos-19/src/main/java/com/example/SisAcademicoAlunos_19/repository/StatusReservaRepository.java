package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusReservaRepository extends JpaRepository<StatusReserva, Long> {
    Optional<StatusReserva> findByCodigo(String codigo);
    List<StatusReserva> findByNome(String nome);
}
