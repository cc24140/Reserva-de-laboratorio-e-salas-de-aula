package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Avaliacao;
import com.example.SisAcademicoAlunos_19.model.Disciplina;
import com.example.SisAcademicoAlunos_19.model.Professor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.transaction.Transactional;

import java.util.List;

@SpringBootTest
class AvaliacaoRepositoryTest
{
    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Test
    public void incluirAvaliacao()
    {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setCodigo("4");
        avaliacao.setNota(5.3);
        avaliacao.setDataHora("2026-04-2311:48");

        // vou escolher um aluno específico
        var alunoEscolhido = 2;
        var aluno = alunoRepository.findById(alunoEscolhido)
                .orElse(null);

        // vou escolher um professor específico
        var professorEscolhido = 4;
        var professor = professorRepository.findById(professorEscolhido)
                .orElse(null);

        // vou escolher uma disciplina específica
        var disciplinaEscolhido = 4;
        var disciplina = disciplinaRepository.findById(disciplinaEscolhido)
                .orElse(null);

        avaliacao.setAluno(aluno);
        avaliacao.setProfessor(professor);
        avaliacao.setDisciplina(disciplina);
        var avalicaoSalva = avaliacaoRepository.save(avaliacao);
        System.out.println("Dados da AVALIACAO salvos: " + avalicaoSalva);
    }


    /*
    @Transactional serve para gerenciar transações de banco de dados garantindo a integridade dos dados.
    Essa notation assegura que todas as operações dentro de um metodo sejam tratadas como um único bloco de trabalho.
    Se a transação executou com sucesso o commit é executado no BD.
    Caso contrário, (ocorrendo uma exceção) o rollback no BD é executado.
    */
    @Test
    @Transactional
    public void listarAvaliacoesAplicadasPeloProfessorX()
    {
        var professorEscolhido = 1;
        Professor professor = professorRepository.findById(professorEscolhido)
                .orElse(null);

        List<Avaliacao> avaliacoes = professor.getAvaliacoes();
        if (avaliacoes != null)
        {
            System.out.println("Exibindo as Avaliações do professor " + professor.getNome() + ": ");
            for (Avaliacao avaliacao : avaliacoes)
            {
                System.out.println("Código da Avaliação = " + avaliacao.getCodigo());
                System.out.println("Data/Hora da Avaliação = " + avaliacao.getDatahora());
                System.out.println("RA = " + avaliacao.getAluno().getRa()
                + " e Nome aluno = " + avaliacao.getAluno().getNome());
                System.out.println("Nota da Avaliação = " + avaliacao.getNota());
            }
        }
        else
        {  System.out.print("A lista de AVALIAÇÕES do professor " + professor.getNome() +
                " está VAZIA!");  }
    }


    @Test
    @Transactional
    public void listarTodosAlunosFizeramAvaliacaoDaDisciplinaX()
    {
        // vou escolher uma disciplina específica
        var disciplinaEscolhida = 2;
        Disciplina disciplina = disciplinaRepository.findById(disciplinaEscolhida)
                .orElse(null);

        List<Avaliacao> avaliacoes = disciplina.getAvaliacoes();
        if (avaliacoes != null)
        {
            System.out.println("ALUNOS que fizeram AVALIAÇÃO da DISCIPLINA  " + disciplina.getNome() + ": ");
            for (Avaliacao avaliacao : avaliacoes)
            {
                System.out.println("RA aluno = " + avaliacao.getAluno().getRa()
                + " e Nome aluno = " + avaliacao.getAluno().getNome());
            }
        }
        else
        {  System.out.print("A lista de AVALIAÇÕES dos ALUNOS da DISCIPLINA " + disciplina.getNome() + " está VAZIA!");   }
    }

}  // end da classe