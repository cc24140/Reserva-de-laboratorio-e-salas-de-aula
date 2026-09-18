package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.Sala;
import com.example.SisAcademicoAlunos_19.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Transactional
    public Sala salvar(Sala sala) {
        if (salaRepository.findByCodigo(sala.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código da sala já cadastrado.");
        }
        return salaRepository.save(sala);
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada."));
    }

    public List<Sala> listarTodos() {
        return salaRepository.findAll();
    }

    public List<Sala> buscarPorFiltros(String nome, Integer capacidade, String localizacao) {
        if (nome != null && !nome.isBlank()) {
            return salaRepository.findByNome(nome);
        }
        if (capacidade != null) {
            return salaRepository.findByCapacidade(capacidade);
        }
        if (localizacao != null && !localizacao.isBlank()) {
            return salaRepository.findByLocalizacao(localizacao);
        }
        return listarTodos();
    }

    @Transactional
    public Sala atualizar(Long id, Sala salaAtualizada) {
        Sala salaExistente = buscarPorId(id);

        if (!salaExistente.getCodigo().equals(salaAtualizada.getCodigo())
                && salaRepository.findByCodigo(salaAtualizada.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código da sala já cadastrado.");
        }

        salaAtualizada.setId(id);
        return salaRepository.save(salaAtualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!salaRepository.existsById(id)) {
            throw new IllegalArgumentException("Sala não encontrada.");
        }
        salaRepository.deleteById(id);
    }
}
