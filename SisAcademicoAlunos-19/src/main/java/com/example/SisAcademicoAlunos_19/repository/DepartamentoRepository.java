package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Departamento;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer>
{
    // parâmetro nominal porque eu uso a notation @Param
    //@Query("select d from Departamento d where d.nome = :nome")
    //List<Departamento> findByNome(@Param("nome") String nome);

    // vou modificar registros numa tabelo do BD
    @Modifying
    // será aberta uma transação de atualização num registro da tabela
    @Transactional
    // paramêtro posicional
    // ?1 = indicação da ordem do paramêtro usado no metodo
    @Query("delete from Departamento where nome = ?1")
    void deleteByNome(String nome);

    @Modifying
    // será aberta uma transação de atualização num registro da tabela
    @Transactional
    @Query("update Departamento set nome = ?1 where id = ?2")
    void updateByNome(String nome, int id);

    List<Departamento> findByLocalizacao(String localizacao);
    List<Departamento> findByFaturamento(Double faturamento);
    List<Departamento> findByResponsavel(String responsavel);

    List<Departamento> findByLocalizacaoAndFaturamentoAndResponsavel(
            String localizacao,
            Double faturamento,
            String responsavel);

    Optional<Departamento> findByNome(String nome);

    Optional<Departamento> findByNomeAndLocalizacaoAndFaturamentoAndResponsavel(
            String nome,
            String localizacao,
            Double faturamento,
            String responsavel);
}
