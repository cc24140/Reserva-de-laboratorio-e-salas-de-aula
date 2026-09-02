package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    Optional<Sala> findByCodigo(String codigo);
    List<Sala> findByNomeContainingIgnoreCase(String nome);
    List<Sala> findByCapacidade(Integer capacidade);
    List<Sala> findByLocalizacaoContainingIgnoreCase(String localizacao);
}
