package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Sala;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SalaRepositoryTest {

    @Autowired
    private SalaRepository salaRepository;

    @Test
    void deveSalvarSala() {
        Sala sala = new Sala();
        sala.setCodigo("SAL_REPO_TEST_001");
        sala.setNome("Sala de Aula 01");
        sala.setCapacidade(40);
        sala.setLocalizacao("Bloco C - 1º andar");

        Sala salvo = salaRepository.save(sala);
        System.out.println("Dados da SALA salva: " + salvo);
    }

    @Test
    void deveBuscarSalaPorLocalizacao() {
        Sala sala = new Sala();
        sala.setCodigo("SAL_REPO_TEST_002");
        sala.setNome("Sala de Aula 02");
        sala.setCapacidade(25);
        sala.setLocalizacao("Bloco D - 3º andar");

        salaRepository.save(sala);
        var resultado = salaRepository.findByLocalizacao("Bloco D");
        System.out.println("Resultado da busca por localização: " + resultado);
    }
}
