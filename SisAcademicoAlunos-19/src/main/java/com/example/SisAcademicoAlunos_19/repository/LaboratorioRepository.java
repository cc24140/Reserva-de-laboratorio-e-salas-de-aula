package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
    Optional<Laboratorio> findByCodigo(String codigo);
    List<Laboratorio> findByNomeContainingIgnoreCase(String nome);
    List<Laboratorio> findByCapacidade(Integer capacidade);
    List<Laboratorio> findByLocalizacaoContainingIgnoreCase(String localizacao);
}
