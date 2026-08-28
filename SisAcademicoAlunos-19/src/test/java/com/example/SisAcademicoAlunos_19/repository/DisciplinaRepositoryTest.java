package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Curso;
import com.example.SisAcademicoAlunos_19.model.Departamento;
import com.example.SisAcademicoAlunos_19.model.Disciplina;
import com.example.SisAcademicoAlunos_19.model.Professor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DisciplinaRepositoryTest
{
    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    DepartamentoRepository departamentoRepository;


    @Test
    public void incluirDisciplina()
    {
        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("CI112");
        disciplina.setNome("Fundamentos Elétricos");
        disciplina.setCargaHoraria(140.00);
        disciplina.setQtdeAulas(5);

        // vou escolher um curso específico
        var cursoEscolhido = 4;
        Curso curso = cursoRepository.findById(cursoEscolhido)
                   .orElse(null);
        disciplina.setCurso(curso);
        var disciplinaSalva = disciplinaRepository.save(disciplina);
        System.out.println("DISCIPLINA incluída = " + disciplinaSalva);
    }

    @Test
    public void atualizaDadosDeUmaDisciplina()
    {
        var idDisciplina = 4;
        Disciplina disciplina = disciplinaRepository.findById(idDisciplina)
                .orElse(null);

        var idCurso = 1;
        Curso curso = cursoRepository.findById(idCurso)
                .orElse(null);

        Disciplina disciplinaASerAtualizada = new Disciplina();
        disciplinaASerAtualizada.setCodigo("TI228");
        disciplinaASerAtualizada.setNome("Sistemas de Computação");
        disciplinaASerAtualizada.setCargaHoraria(200);
        disciplinaASerAtualizada.setQtdeAulas(2);
        disciplinaASerAtualizada.setCurso(curso);
        var disciplinaSalva = disciplinaRepository.save(disciplinaASerAtualizada);
        System.out.println("Dados da DISCIPLINA atualizada = " + disciplinaSalva);
    }

    @Test
    public void apagarDisciplinaPorID()
    {
        var idDisciplina = 9;
        disciplinaRepository.deleteById(idDisciplina);
        System.out.println("Disciplina id = " + idDisciplina + " excluída do BD");
    }

    @Test
    public void buscarDadosDisciplinaPorID()
    {
        var idDisciplina = 4;
        Disciplina disciplina = disciplinaRepository.findById(idDisciplina)
                .orElse(null);
        System.out.println("Dados da Disciplina");
        System.out.println("Código da disciplina = " + disciplina.getCodigo());
        System.out.println("Nome da disciplina = " + disciplina.getNome());
        System.out.println("Carga Horária da disciplina = " + disciplina.getCargaHoraria());
        System.out.println("Qtde de Aulas da disciplina = " + disciplina.getQtdeAulas());
        System.out.println("Nome do CURSO da Disciplina ID " + disciplina.getCodigo() + " = "
                + disciplina.getCurso());
    }

    @Test
    public void buscarDadosDaDisciplinaECursoEDepartamento()
    {
        var idCurso = 1;
        Departamento departamento = departamentoRepository.findById(idCurso)
                .orElse(null);

        var idDisciplina = 4;
        Disciplina disciplina = disciplinaRepository.findById(idDisciplina)
                .orElse(null);

        System.out.println("Dados da Disciplina");
        System.out.println("Código da disciplina = " + disciplina.getCodigo());
        System.out.println("Nome da disciplina = " + disciplina.getNome());
        System.out.println("Carga Horária da disciplina = " + disciplina.getCargaHoraria());
        System.out.println("Qtde de Aulas da disciplina = " + disciplina.getQtdeAulas());
        System.out.println("Nome do CURSO da Disciplina ID " + disciplina.getCodigo() + " = "
                + disciplina.getCurso());
        System.out.println("Nome do DEPARTAMENTO do CURSO " + disciplina.getCurso() + " = "
                + departamento.getNome());
    }

    @Test
    @Transactional
    public void buscarTodasDisciplinasLecionadasPeloProfessorX()
    {
        // escolhi um professor especifico
        var idProfessorEscolhido = 1;
        Professor professor = professorRepository.findById(idProfessorEscolhido)
                .orElse(null);
        List<Disciplina> disciplinas = professor.getDisciplinas();
        if (disciplinas != null)
        {
            System.out.println("Exibindo as Disciplinas lecionadas do professor: " + professor.getNome());
            for (Disciplina disciplina : disciplinas)
            {
                System.out.println("Código da disciplina = " + disciplina.getCodigo());
                System.out.println("Nome da disciplina = " + disciplina.getNome());
            }
        }
        else
        {
            System.out.print("A lista de DISCIPLINAS do PROFESSOR "
                    + professor.getNome() + " está VAZIA!");
        }
    }

    @Test
    public void listarTodasAsDisciplinas()
    {
        List<Disciplina> listaDisciplina =  disciplinaRepository.findAll();
        System.out.println("Exibindo TODAS as disciplinas cadastradas:");
        if (listaDisciplina != null)
        {
            System.out.println("Exibindo as Disciplinas");
            for (Disciplina d : listaDisciplina)
            {  System.out.println(d.getNome());  }
        }
        else
        {   System.out.print("A lista de DISCIPLINAS está VAZIA!");  }
    }

}  // end da classe