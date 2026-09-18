package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> { // comunicação direta com o banco, faz consultas, save, findAll, delete
    // consulta personalizada para buscar status por código
    Optional<Status> findByCodigo(String codigo);

    // consulta personalizada para buscar status por nome
    List<Status> findByNome(String nome);
}
