package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Aluno;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlunoRepositoryTest
{
    @Autowired
    AlunoRepository repository;
    @Autowired
    private DataSourcePoolMetadataProvider hikariPoolDataSourceMetadataProvider;

    @Test
    public void incluirAluno()
    {
        Aluno aluno = new Aluno();
        aluno.setRA("26145");
        aluno.setNome("Lais Prado");
        aluno.setEmail("lala@unicamp.br");
        var alunoSalvo = repository.save(aluno);
        System.out.println("Dados do ALUNO salvos: " + alunoSalvo);
    }
}