package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Aluno;
import com.example.SisAcademicoAlunos_19.model.Avaliacao;
import com.example.SisAcademicoAlunos_19.model.Curso;
import com.example.SisAcademicoAlunos_19.model.Matricula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class MatriculaRepositoryTest {
    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Test
    public void incluirMatricula() {
        Matricula matricula = new Matricula();
        matricula.setCodigo("20");
        matricula.setData("2026-04-24");
        LocalTime horaEspecifica = LocalTime.of(20, 10);
        matricula.setHora(Time.valueOf(horaEspecifica));

        // vou escolher um curso específico
        var cursoEscolhido = 2;
        var curso = cursoRepository.findById(cursoEscolhido)
                .orElse(null);
        matricula.setCurso(curso);

        // vou escolher um aluno específico
        var alunoEscolhido = 4;
        var aluno = alunoRepository.findById(alunoEscolhido)
                .orElse(null);
        matricula.setAluno(aluno);

        var matriculaSalva = matriculaRepository.save(matricula);
        System.out.println("Dados da DISCIPLINA salvos: " + matriculaSalva);
    }

    @Test
    public void atualizarMatricula() {
        // vou escolher uma matricula específica
        var matriculaEscolhida = 1;
        Optional<Matricula> possivelMatricula = matriculaRepository.findById(matriculaEscolhida);
        if (possivelMatricula.isPresent()) {
            Matricula matriculaSelecionada = possivelMatricula.get();
            matriculaSelecionada.setCodigo("20");
            matriculaSelecionada.setData("2026-04-24");
            LocalTime horaEspecifica = LocalTime.of(20, 30);
            matriculaSelecionada.setHora(Time.valueOf(horaEspecifica));

            // vou escolher um curso específico
            var cursoEscolhido = 2;
            var curso = cursoRepository.findById(cursoEscolhido)
                    .orElse(null);
            matriculaSelecionada.setCurso(curso);

            var matriculaAtualizada = matriculaRepository.save(matriculaSelecionada);
            System.out.println("Dados da DISCIPLINA atualizados: " + matriculaAtualizada);
        }
    }

    @Test
    public void exibirDadosDeUmaMatricula() {
        // vou escolher uma matricula específica
        var matriculaEscolhida = 1;
        Matricula matricula = matriculaRepository.findById(matriculaEscolhida)
                .orElse(null);
        System.out.println("Dados da MATRÍCULA selecionada...");
        System.out.println("Código: " + matricula.getCodigo());
        System.out.println("Data: " + matricula.getData());
        System.out.println("Hora: " + matricula.getHora());
        System.out.println("Curso: " + matricula.getCurso().getNome());
        System.out.println("Aluno: " + matricula.getAluno().getNome());
    }

    @Test
    public void excluirMatricula() {
        // vou escolher uma matricula específica
        var matriculaEscolhida = 4;
        matriculaRepository.deleteById(matriculaEscolhida);
        System.out.println("Matrícula ID = " + matriculaEscolhida + " excluída do BD");
    }

    @Test
    public void exibirDadosSolicitadosDeMatricula() {
        // vou escolher uma matricula específica
        var matriculaEscolhida = 1;
        Matricula matricula = matriculaRepository.findById(matriculaEscolhida)
                .orElse(null);

        System.out.println("Seguem os dados da MATRÍCULA escolhida");
        System.out.println("CÓDIGO: " + matricula.getCodigo());
        System.out.println("DATA: " + matricula.getData());

        Curso curso = matricula.getCurso();
        System.out.println("CURSO: " + curso.getNome());

        Aluno aluno = matricula.getAluno();
        System.out.println("RA do ALUNO: " + aluno.getRa());
        System.out.println("NOME do ALUNO: " + aluno.getNome());
    }

    @Test
    public void pesquisarMatriculasPorIntervalorDatas() {
        List<Matricula> matricula = matriculaRepository.findByDataBetween("2026-04-24", "2026-04-24");
        for (Matricula lista : matricula) {
            System.out.println("Código da matrícula = " + lista.getCodigo());
            System.out.println("Data da matrícual = " + lista.getData());
        }
    }
}