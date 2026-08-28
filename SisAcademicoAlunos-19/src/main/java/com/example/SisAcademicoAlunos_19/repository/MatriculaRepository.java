package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer>
{
    List<Matricula> findByDataBetween(String dtInicio, String dtFim);
}
