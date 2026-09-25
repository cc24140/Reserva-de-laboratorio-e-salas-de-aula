package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.StatusReserva;
import com.example.SisAcademicoAlunos_19.repository.StatusReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusReservaService {

    private final StatusReservaRepository statusReservaRepository;

    public StatusReservaService(StatusReservaRepository statusReservaRepository) {
        this.statusReservaRepository = statusReservaRepository;
    }

    @Transactional
    public StatusReserva salvar(StatusReserva statusReserva) {
        if (statusReservaRepository.findByCodigo(statusReserva.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código do status da reserva já cadastrado.");
        }
        return statusReservaRepository.save(statusReserva);
    }

    public StatusReserva buscarPorId(Long id) {
        return statusReservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Status da reserva não encontrado."));
    }

    public List<StatusReserva> listarTodos() {
        return statusReservaRepository.findAll();
    }

    public List<StatusReserva> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return listarTodos();
        }
        return statusReservaRepository.findByNome(nome);
    }

    @Transactional
    public StatusReserva atualizar(Long id, StatusReserva statusReservaAtualizado) {
        StatusReserva statusExistente = buscarPorId(id);
        if (!statusExistente.getCodigo().equals(statusReservaAtualizado.getCodigo())
                && statusReservaRepository.findByCodigo(statusReservaAtualizado.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código do status da reserva já cadastrado.");
        }
        statusReservaAtualizado.setId(id);
        return statusReservaRepository.save(statusReservaAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!statusReservaRepository.existsById(id)) {
            throw new IllegalArgumentException("Status da reserva não encontrado.");
        }
        statusReservaRepository.deleteById(id);
    }
}
