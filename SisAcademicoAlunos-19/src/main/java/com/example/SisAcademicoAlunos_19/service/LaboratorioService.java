package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import com.example.SisAcademicoAlunos_19.repository.LaboratorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;

    public LaboratorioService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    @Transactional
    public Laboratorio salvar(Laboratorio laboratorio) {
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
}
