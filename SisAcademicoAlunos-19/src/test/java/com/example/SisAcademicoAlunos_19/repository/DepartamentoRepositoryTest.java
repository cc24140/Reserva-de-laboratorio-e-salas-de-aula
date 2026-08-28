package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Departamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DepartamentoRepositoryTest
{
    @Autowired
    DepartamentoRepository departamentoRepository;

    @Test
    public void incluirDepartamento()
    {
        Departamento departamento = new Departamento();
        departamento.setNome("Departamento de Teste");
        var departamentoSalvo = departamentoRepository.save(departamento);
        System.out.println("Dados do DEPARTAMENTO salvo: " + departamentoSalvo);
    }


    @Test
    public void pesquisarDeparmentoPorNomeEParametros()
    {
        //List<Departamento> lista = departamentoRepository.findByNome("Departamento de Teste Denovo");
        //for (Departamento listaDepto : lista) {
        //    System.out.println("Id Departamento escolhido = " + listaDepto.getId() + " - Nome = " + listaDepto.getNome());
        //}
    }


    @Test
    public void excluirDepartamentoPorNome()
    {
        departamentoRepository.deleteByNome("Departamento de Teste Denovo");
        System.out.println("Curso foi excluído com sucesso'");
    }

    @Test
    public void atualizarNomeDoDepartamentoPorId()
    {
        departamentoRepository.updateByNome("Departamento de Teste Denovo",6);
        System.out.println("Curso foi autualizado com sucesso'");
    }

}