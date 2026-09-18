package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    // consulta personalizada para buscar salas por código
    Optional<Sala> findByCodigo(String codigo);

    // consulta personalizada para buscar salas por nome
    List<Sala> findByNome(String nome);

    // consulta personalizada para buscar salas por capacidade
    List<Sala> findByCapacidade(Integer capacidade);

    // consulta personalizada para buscar salas por localização
    List<Sala> findByLocalizacao(String localizacao);
}
