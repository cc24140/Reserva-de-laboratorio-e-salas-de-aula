package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.Status;
import com.example.SisAcademicoAlunos_19.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Transactional
    public Status salvar(Status status) {
        if (statusRepository.findByCodigo(status.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código de status já cadastrado.");
        }
        return statusRepository.save(status);
    }

    public Status buscarPorId(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Status não encontrado."));
    }

    public List<Status> listarTodos() {
        return statusRepository.findAll();
    }

    public List<Status> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return listarTodos();
        }
        return statusRepository.findByNome(nome);
    }

    @Transactional
    public Status atualizar(Long id, Status statusAtualizado) {
        Status statusExistente = buscarPorId(id);

        if (!statusExistente.getCodigo().equals(statusAtualizado.getCodigo())
                && statusRepository.findByCodigo(statusAtualizado.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código de status já cadastrado.");
        }

        statusAtualizado.setId(id);
        return statusRepository.save(statusAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!statusRepository.existsById(id)) {
            throw new IllegalArgumentException("Status não encontrado.");
        }
        statusRepository.deleteById(id);
    }
}
