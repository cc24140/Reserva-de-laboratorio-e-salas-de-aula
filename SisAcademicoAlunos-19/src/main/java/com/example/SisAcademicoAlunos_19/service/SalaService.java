package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.Sala;
import com.example.SisAcademicoAlunos_19.model.Status;
import com.example.SisAcademicoAlunos_19.repository.SalaRepository;
import com.example.SisAcademicoAlunos_19.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaService {  // chama o repository

    private final SalaRepository salaRepository;
    private final StatusRepository statusRepository;

    public SalaService(SalaRepository salaRepository, StatusRepository statusRepository) {
        this.salaRepository = salaRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional
    public Sala salvar(Sala sala) {
        validarSala(sala);
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
        validarSala(salaAtualizada);

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

    private void validarSala(Sala sala) {
        if (sala.getNome() == null || sala.getNome().trim().isEmpty() || sala.getNome().length() < 10 || sala.getNome().length() > 80) {
            throw new IllegalArgumentException("Quantidade de caracteres incorreta!");
        }
        if (sala.getCapacidade() == null || sala.getCapacidade() < 1 || sala.getCapacidade() > 40) {
            throw new IllegalArgumentException("Valor fora do escopo");
        }
        if (sala.getLocalizacao() == null || sala.getLocalizacao().trim().isEmpty() || sala.getLocalizacao().length() < 15 || sala.getLocalizacao().length() > 50) {
            throw new IllegalArgumentException("Quantidade de caracteres incorreta!");
        }
        if (sala.getStatus() != null && sala.getStatus().getId() != null) {
            Status status = statusRepository.findById(sala.getStatus().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Status do recurso não encontrado."));
            sala.setStatus(status);
        }
    }
}
