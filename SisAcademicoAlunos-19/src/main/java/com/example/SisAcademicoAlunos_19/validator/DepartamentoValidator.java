package com.example.SisAcademicoAlunos_19.validator;

import com.example.SisAcademicoAlunos_19.exceptions.RegistroDuplicadoException;
import com.example.SisAcademicoAlunos_19.model.Departamento;
import com.example.SisAcademicoAlunos_19.repository.DepartamentoRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class DepartamentoValidator
{
    private DepartamentoRepository departamentoRepository;

    public DepartamentoValidator(
            DepartamentoRepository departamentoRepository)
    {
        this.departamentoRepository = departamentoRepository;
    }

    public void validar(Departamento departamento)
    {
        // verificar se já existe o departamento cadastrado
        if (existeDepartamentoCadastrado(departamento))
        {
            throw new RegistroDuplicadoException(
                    "Departamento já cadastrado");
        }
    }

    private boolean existeDepartamentoCadastrado(Departamento departamento) {
        Optional<Departamento> departamentoEncontrado = departamentoRepository.findByNomeAndLocalizacaoAndFaturamentoAndResponsavel(
                departamento.getNome(),
                departamento.getLocalizacao(),
                departamento.getFaturamento(),
                departamento.getResponsavel());

        // verificando se está sendo feita uma inclusão no BD de um
        // novo registro de DEPARTAMENTO
        if (departamento.getId() == null) {
            return departamentoEncontrado.isPresent();
        }

        // no caso de ser atualização de um registro de DEPARTAMENTO
        // já existente no BD
        return departamentoEncontrado.isPresent() &&
                !departamento.getId().equals(departamentoEncontrado.get().getId());
    }
}