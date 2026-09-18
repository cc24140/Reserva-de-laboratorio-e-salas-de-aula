package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {   // comunicação direta com o banco, faz consultas, save, findAll, delete
    Optional<Laboratorio> findByCodigo(String codigo);
    List<Laboratorio> findByNome(String nome);
    List<Laboratorio> findByCapacidade(Integer capacidade);
    List<Laboratorio> findByLocalizacao(String localizacao);
}
