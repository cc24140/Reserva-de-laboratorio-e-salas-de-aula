package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Disciplina;
import com.example.SisAcademicoAlunos_19.model.Professor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ProfessorRepositoryTest
{
    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    DepartamentoRepository departamentoRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;


    @Test
    public void incluirProfessor()
    {
        Professor professor = new Professor();
        professor.setMatricula("28390");
        professor.setNome("Matheus Santos");
        professor.setCelular("95512-4578");
        professor.setEmail("santos@gmail.com");
        professor.setDataNascimento(Date.valueOf(LocalDate.of(1983, 12, 04)));

        // vou escolher um departamento específico
        var departamentoEscolhido = 2;
        var departamentoSalvo = departamentoRepository.findById(departamentoEscolhido)
                .orElse(null);
        professor.setDepartamento(departamentoSalvo);
        professorRepository.save(professor);
    }


    @Test
    public void listarProfessoresDoDepartamentoX()
    {
        // escolhi um departamento especifico
        var departamentoEscolhido = 1;
        List<Professor> listaProfessores = professorRepository.findByDepartamentoId(departamentoEscolhido);
        System.out.println("Professores TODOS OS Professores do Departamento CÓDIGO = " + departamentoEscolhido);
        for (int indice = 0; indice < listaProfessores.size(); indice++)
        {
            Professor prof = listaProfessores.get(indice);
            System.out.println(
                    "Matrícula: " + prof.getMatricula() + " - " +
                    "Nome: " + prof.getNome()
            );
        }
    }
}