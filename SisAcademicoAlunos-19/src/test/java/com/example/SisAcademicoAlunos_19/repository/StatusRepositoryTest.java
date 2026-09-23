package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatusRepositoryTest {

    @Autowired
    private StatusRepository statusRepository;

    @Test
    void deveSalvarStatus() {
        Status status = new Status();
        status.setCodigo("STAT_LIVRE_01");
        status.setNome("Livre");

        Status salvo = statusRepository.save(status);
        System.out.println("Dados do STATUS salvo: " + salvo);
    }

    @Test
    void deveBuscarStatusPorCodigo() {
        Status status = new Status();
        status.setCodigo("STAT_OCUP_01");
        status.setNome("Ocupado");

        statusRepository.save(status);
        var resultado = statusRepository.findByCodigo("STAT_OCUP_01");
        System.out.println("Resultado da busca por código: " + resultado);
    }
}
