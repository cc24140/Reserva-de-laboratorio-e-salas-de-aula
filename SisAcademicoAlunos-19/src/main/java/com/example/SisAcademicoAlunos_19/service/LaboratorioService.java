package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import com.example.SisAcademicoAlunos_19.model.Status;
import com.example.SisAcademicoAlunos_19.repository.LaboratorioRepository;
import com.example.SisAcademicoAlunos_19.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LaboratorioService {   // chama o repository

    private final LaboratorioRepository laboratorioRepository;
    private final StatusRepository statusRepository;

    public LaboratorioService(LaboratorioRepository laboratorioRepository, StatusRepository statusRepository) {
        this.laboratorioRepository = laboratorioRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional
    public Laboratorio salvar(Laboratorio laboratorio) {
        validarLaboratorio(laboratorio);
        if (laboratorioRepository.findByCodigo(laboratorio.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código do laboratório já cadastrado.");
        }
        return laboratorioRepository.save(laboratorio);
    }

    public Laboratorio buscarPorId(Long id) {
        return laboratorioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Laboratório não encontrado."));
    }

    public List<Laboratorio> listarTodos() {
        return laboratorioRepository.findAll();
    }

    public List<Laboratorio> buscarPorFiltros(String nome, Integer capacidade, String localizacao) {
        if (nome != null && !nome.isBlank()) {
            return laboratorioRepository.findByNome(nome);
        }
        if (capacidade != null) {
            return laboratorioRepository.findByCapacidade(capacidade);
        }
        if (localizacao != null && !localizacao.isBlank()) {
            return laboratorioRepository.findByLocalizacao(localizacao);
        }
        return listarTodos();
    }

    @Transactional
    public Laboratorio atualizar(Long id, Laboratorio laboratorioAtualizado) {
        Laboratorio laboratorioExistente = buscarPorId(id);
        validarLaboratorio(laboratorioAtualizado);

        if (!laboratorioExistente.getCodigo().equals(laboratorioAtualizado.getCodigo())
                && laboratorioRepository.findByCodigo(laboratorioAtualizado.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código do laboratório já cadastrado.");
        }

        laboratorioAtualizado.setId(id);
        return laboratorioRepository.save(laboratorioAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!laboratorioRepository.existsById(id)) {
            throw new IllegalArgumentException("Laboratório não encontrado.");
        }
        laboratorioRepository.deleteById(id);
    }

    private void validarLaboratorio(Laboratorio laboratorio) {
        if (laboratorio.getNome() == null || laboratorio.getNome().trim().isEmpty() || laboratorio.getNome().length() < 10 || laboratorio.getNome().length() > 80) {
            throw new IllegalArgumentException("Quantidade de caracteres incorreta!");
        }
        if (laboratorio.getCapacidade() == null || laboratorio.getCapacidade() < 1 || laboratorio.getCapacidade() > 40) {
            throw new IllegalArgumentException("Valor fora do escopo");
        }
        if (laboratorio.getLocalizacao() == null || laboratorio.getLocalizacao().trim().isEmpty() || laboratorio.getLocalizacao().length() < 15 || laboratorio.getLocalizacao().length() > 50) {
            throw new IllegalArgumentException("Quantidade de caracteres incorreta!");
        }
        if (laboratorio.getStatus() != null && laboratorio.getStatus().getId() != null) {
            Status status = statusRepository.findById(laboratorio.getStatus().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Status do recurso não encontrado."));
            laboratorio.setStatus(status);
        }
    }
}
