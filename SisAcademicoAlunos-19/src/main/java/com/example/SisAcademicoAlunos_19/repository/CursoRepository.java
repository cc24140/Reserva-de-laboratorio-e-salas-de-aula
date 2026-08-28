package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Curso;
import com.example.SisAcademicoAlunos_19.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer>
{
    // metodo de Query
    // Pesquisar curso por nome
    List<Curso> findByNome(String nome);

    // metodo de pesquisa utilizando mais de 1 campo com AND
    List<Curso> findByCodigoAndNome(Integer codigo, String nome);

    // metodo de pesquisa utilizando mais de um campo com OR
    List<Curso> findByNomeOrCargaHoraria(String nome, double carga);

    // usar a @Query
    // select * from curso order by nome
    // JPQL --> referencia as entidades e as propriedades da entidade
    @Query("select c from Curso as c order by c.nome")
    List<Curso> listarTodosCursosOrdenadorPorNome();

    @Query("select c from Curso as c order by c.codigo, c.nome")
    List<Curso> listarTodosCursosOrdenadorPorCodigoENome();

    //select d.*
    //from curso c
    //join departamento d on d.id = c.id_departamento
    @Query("select d from Curso c join c.departamento d")
    List<Departamento> listarCursosPorDepartamento();


    boolean existsByDepartamento(Departamento departamento);
}
