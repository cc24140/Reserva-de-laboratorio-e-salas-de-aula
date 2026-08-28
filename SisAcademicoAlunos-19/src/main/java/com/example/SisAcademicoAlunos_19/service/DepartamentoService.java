package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.exceptions.OperacaoNaoPermitidaException;
import com.example.SisAcademicoAlunos_19.model.Departamento;
import com.example.SisAcademicoAlunos_19.repository.CursoRepository;
import com.example.SisAcademicoAlunos_19.repository.DepartamentoRepository;
import com.example.SisAcademicoAlunos_19.validator.DepartamentoValidator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService
{
    private final DepartamentoRepository departamentoRepository;
    private final DepartamentoValidator departamentoValidator;
    private final CursoRepository cursoRepository;

    public DepartamentoService(
            DepartamentoRepository repository,
            DepartamentoValidator departamentoValidator,
            CursoRepository cursoRepository)
    {
        this.departamentoRepository = repository;
        this.departamentoValidator = departamentoValidator;
        this.cursoRepository = cursoRepository;
    }

    public Departamento inserirDepartamento(Departamento departamento)
    {
        // validar os campos que vieram do JSON antes de incluir no BD
        departamentoValidator.validar(departamento);
        return departamentoRepository.save(departamento);
    }

    public Optional<Departamento> pegarDadosDepartamentoPorId(Integer id)
    {
        return departamentoRepository.findById(id);
    }

    public void excluirDepartamentoPorId(Integer id)
    {
        if (possuiCurso(id))
        {
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um Departamento que " +
                    "possui 1 ou mais cursos associados ao departamento!");
        }
        departamentoRepository.deleteById(id);
    }


    public Departamento atualizarDepartamento(Departamento departamento)
    {
        if (departamento.getId() == null)
        {
            throw new IllegalArgumentException(
                    "Não existe o DEPARTAMENTO com o ID informado.");
        }
        // validar os campos que vieram do JSON antes de atualizar no BD
        departamentoValidator.validar(departamento);
        return departamentoRepository.save(departamento);
    }

    public List<Departamento> pesquisarPorLocalizacaoFaturamentoResponsavel(
            String localizacao, Double faturamento, String responsavel)
    {
        if (localizacao != null
                && faturamento != null
                && responsavel != null)
        {
            return departamentoRepository.findByLocalizacaoAndFaturamentoAndResponsavel(
                    localizacao, faturamento, responsavel);
        }

        if (localizacao != null)  {
            return departamentoRepository.findByLocalizacao(localizacao); }

        if (faturamento != null) {
            return departamentoRepository.findByFaturamento(faturamento); }

        if (responsavel != null) {
            return departamentoRepository.findByResponsavel(responsavel); }

        return departamentoRepository.findAll();
    }

    public boolean possuiCurso(Integer iddepartamento)
    {
        Optional<Departamento> departamento = departamentoRepository.findById(iddepartamento);
        return cursoRepository.existsByDepartamento(departamento.get());
    }
}
