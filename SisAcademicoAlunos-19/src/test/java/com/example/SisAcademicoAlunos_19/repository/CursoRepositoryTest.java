package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Curso;
import com.example.SisAcademicoAlunos_19.model.Departamento;
import com.example.SisAcademicoAlunos_19.model.Disciplina;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CursoRepositoryTest {
    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    DepartamentoRepository departamentoRepository;

    @Test
    public void incluirCurso()
    {
        Curso curso = new Curso();
        curso.setCodigo("25");
        curso.setNome("Desenvolvimento Circuitos Eletricos");
        curso.setCargaHoraria(690.20);

        // vou escolher um departamento específico
        var departamentoEscolhido = 2;
        var departamento = departamentoRepository.findById(departamentoEscolhido)
                .orElse(null);
        curso.setDepartamento(departamento);
        var cursoSalvo = cursoRepository.save(curso);
        System.out.println("Dados do CURSO salvos: " + cursoSalvo);
    }

    @Test
    public void atualizarCurso()
    {
        // pegar o ID de um registro da tabela CURSO existente
        var id = 4;
        Optional<Curso> possivelCurso = cursoRepository.findById(id);
        if (possivelCurso.isPresent()) {
            Curso cursoEncontrado = possivelCurso.get();
            System.out.println("Dados do CURSO seleciondado: ");
            System.out.println(cursoEncontrado);
            cursoEncontrado.setNome("Enfermagem Avançado");
            cursoEncontrado.setCargaHoraria(1100.88);
            cursoRepository.save(cursoEncontrado);
        }
    }

    @Test
    public void listarTodosRegistrosCurso()
    {
        List<Curso> listaCurso = cursoRepository.findAll();
        System.out.println("Exibindo TODOS os registros da tabela CURSO: ");
        listaCurso.forEach(System.out::println);
    }

    @Test
    public void contarRegistrosDaTabelaCurso()
    {
        System.out.println("Total de registros da tabela CURSO = " +
                cursoRepository.count());
    }

    @Test
    public void apagarRegistroDaTabelaCursoPorID()
    {
        // pegar o ID de um registro da tabela CURSO existente
        var id = 6;
        cursoRepository.deleteById(id);
        System.out.println("Registro id = " + id + " excluído da tabela CURSO!");
    }

    @Test
    public void pesquisarCursoPorNome()
    {
        List<Curso> listaDeCursos = cursoRepository.findByNome("Desenvolvimento Internet");
        for (Curso lista : listaDeCursos)
        {
            System.out.println("Código do curso = " + lista.getCodigo() + " - Nome do curso" + lista.getNome());
        }
    }

     @Test
     public void pesquisarCursoPorCodigoANDNome()
     {
         List<Curso> lista = cursoRepository.findByCodigoAndNome(39,"Desenvolvimento Internet");
         System.out.println("Código do Curso = " + lista.getFirst().getCodigo() + " - Nome do curso = " + lista.getFirst().getNome());
     }

    @Test
    public void pesquisarCursoPorCodigoORCargaHoraria()
    {
        List<Curso> lista = cursoRepository.findByNomeOrCargaHoraria("Desenvolvimento Internet", 789);
        for (Curso listaCursos : lista) {
            System.out.println("Código do curso = " + listaCursos.getCodigo() +  " - Nome do curso = " + listaCursos.getNome());
            System.out.println("Carga Horária = " + listaCursos.getCargaHoraria());
        }
    }

    @Test
    public void listarTodosCursosOrdenadorPorNomeComJPQL()
    {
        List<Curso> listaOrdenada = cursoRepository.listarTodosCursosOrdenadorPorNome();
        for (Curso listaCursos : listaOrdenada) {
            System.out.println("Código do curso = " + listaCursos.getCodigo() + " - Nome do curso = " + listaCursos.getNome());
        }
    }

    @Test
    public void listarTodosCursosOrdenadorPorCodigoENomeComJPQL()
    {
        List<Curso> listaOrdenada = cursoRepository.listarTodosCursosOrdenadorPorCodigoENome();
        for (Curso listaCursos : listaOrdenada) {
            System.out.println("Código do curso = " + listaCursos.getCodigo() + " - Nome do curso = " + listaCursos.getNome());
        }
    }

    @Test
    public void listarDepartamentoPorCurso()
    {
        List<Departamento> lista = cursoRepository.listarCursosPorDepartamento();
        for (Departamento listaDepto : lista)
        {
            System.out.println("Nome do Departamento = " + listaDepto.getNome());
        }
    }






}

