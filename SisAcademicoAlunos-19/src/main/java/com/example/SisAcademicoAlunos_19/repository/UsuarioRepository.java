package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // consulta personalizada para buscar usuários por login
    Optional<Usuario> findByLogin(String login);

    // consulta personalizada para buscar usuários por email para reservas
    List<Usuario> findByEmail(String email);

    // consulta personalizada para buscar usuários por data de aniversario para reservas
    @Query("SELECT u FROM Usuario u WHERE u.dataAniversario = :data")
    List<Usuario> findByDataAniversario(@Param("data") LocalDate data);
}
