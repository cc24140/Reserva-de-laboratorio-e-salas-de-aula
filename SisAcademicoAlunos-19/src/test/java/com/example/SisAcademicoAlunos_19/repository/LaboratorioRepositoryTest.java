package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LaboratorioRepositoryTest {

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Test
    void deveSalvarLaboratorio() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setCodigo("LAB_REPO_TEST_001");
        laboratorio.setNome("Laboratório de Redes");
        laboratorio.setCapacidade(30);
        laboratorio.setLocalizacao("Bloco A - Térreo");

        Laboratorio salvo = laboratorioRepository.save(laboratorio);
        System.out.println("Dados do LABORATÓRIO salvo: " + salvo);
    }

    @Test
    void deveBuscarLaboratorioPorNome() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setCodigo("LAB_REPO_TEST_002");
        laboratorio.setNome("Laboratório de Banco de Dados");
        laboratorio.setCapacidade(20);
        laboratorio.setLocalizacao("Bloco B - 2º andar");

        laboratorioRepository.save(laboratorio);
        var resultado = laboratorioRepository.findByNome("Banco");
        System.out.println("Resultado da busca por nome: " + resultado);
    }
}
