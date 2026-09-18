package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import com.example.SisAcademicoAlunos_19.model.Reserva;
import com.example.SisAcademicoAlunos_19.model.Sala;
import com.example.SisAcademicoAlunos_19.model.Status;
import com.example.SisAcademicoAlunos_19.model.Usuario;
import com.example.SisAcademicoAlunos_19.repository.LaboratorioRepository;
import com.example.SisAcademicoAlunos_19.repository.ReservaRepository;
import com.example.SisAcademicoAlunos_19.repository.SalaRepository;
import com.example.SisAcademicoAlunos_19.repository.StatusRepository;
import com.example.SisAcademicoAlunos_19.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService { // chama o repository

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LaboratorioRepository laboratorioRepository;
    private final SalaRepository salaRepository;
    private final StatusRepository statusRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioRepository usuarioRepository,
                          LaboratorioRepository laboratorioRepository,
                          SalaRepository salaRepository,
                          StatusRepository statusRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.laboratorioRepository = laboratorioRepository;
        this.salaRepository = salaRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional
    public Reserva salvar(Reserva reserva) {
        validarReserva(reserva);

        if (reserva.getLaboratorio() != null && reserva.getSala() != null) {
            throw new IllegalArgumentException("Uma reserva deve estar vinculada a apenas um recurso: laboratório ou sala.");
        }

        if (reserva.getLaboratorio() == null && reserva.getSala() == null) {
            throw new IllegalArgumentException("Informe um laboratório ou uma sala para a reserva.");
        }

        verificarConflito(reserva);
        return reservaRepository.save(reserva);
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));
    }

    public List<Reserva> listarTodos() {
        return reservaRepository.findAll();
    }

    public List<Reserva> buscarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuario_Id(usuarioId);
    }

    public List<Reserva> buscarPorData(LocalDate data) {
        return reservaRepository.findByDataInicio(data);
    }

    public List<Reserva> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return reservaRepository.findByDataInicioBetween(dataInicio, dataFim);
    }

    @Transactional
    public Reserva atualizar(Long id, Reserva reservaAtualizada) {
        Reserva reservaExistente = buscarPorId(id);

        reservaAtualizada.setId(id);
        validarReserva(reservaAtualizada);

        if (reservaAtualizada.getLaboratorio() != null && reservaAtualizada.getSala() != null) {
            throw new IllegalArgumentException("Uma reserva deve estar vinculada a apenas um recurso: laboratório ou sala.");
        }

        if (reservaAtualizada.getLaboratorio() == null && reservaAtualizada.getSala() == null) {
            throw new IllegalArgumentException("Informe um laboratório ou uma sala para a reserva.");
        }

        verificarConflito(reservaAtualizada, id);
        return reservaRepository.save(reservaAtualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }
        reservaRepository.deleteById(id);
    }

    private void validarReserva(Reserva reserva) {
        if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário da reserva é obrigatório.");
        }

        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário informado não existe."));
        reserva.setUsuario(usuario);

        if (reserva.getStatus() == null || reserva.getStatus().getId() == null) {
            throw new IllegalArgumentException("Status da reserva é obrigatório.");
        }

        Status status = statusRepository.findById(reserva.getStatus().getId())
                .orElseThrow(() -> new IllegalArgumentException("Status informado não existe."));
        reserva.setStatus(status);

        if (reserva.getDataInicio() == null || reserva.getDataFim() == null) {
            throw new IllegalArgumentException("As datas inicial e final são obrigatórias.");
        }

        if (reserva.getHoraInicio() == null || reserva.getHoraFim() == null) {
            throw new IllegalArgumentException("As horas inicial e final são obrigatórias.");
        }

        if (reserva.getDataFim().isBefore(reserva.getDataInicio())) {
            throw new IllegalArgumentException("A data final não pode ser anterior à data inicial.");
        }

        if (reserva.getHoraFim().isBefore(reserva.getHoraInicio()) || reserva.getHoraFim().equals(reserva.getHoraInicio())) {
            throw new IllegalArgumentException("A hora final deve ser maior que a hora inicial.");
        }

        if (reserva.getLaboratorio() != null && reserva.getLaboratorio().getId() != null) {
            Laboratorio laboratorio = laboratorioRepository.findById(reserva.getLaboratorio().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Laboratório informado não existe."));
            reserva.setLaboratorio(laboratorio);
        }

        if (reserva.getSala() != null && reserva.getSala().getId() != null) {
            Sala sala = salaRepository.findById(reserva.getSala().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Sala informada não existe."));
            reserva.setSala(sala);
        }
    }

    private void verificarConflito(Reserva reserva) {
        verificarConflito(reserva, null);
    }

    private void verificarConflito(Reserva reserva, Long idIgnorado) {
        Long laboratorioId = reserva.getLaboratorio() != null ? reserva.getLaboratorio().getId() : null;
        Long salaId = reserva.getSala() != null ? reserva.getSala().getId() : null;

        List<Reserva> conflitos = reservaRepository.buscarReservasConflitantes(
                laboratorioId,
                salaId,
                reserva.getDataInicio(),
                reserva.getDataFim(),
                reserva.getHoraInicio(),
                reserva.getHoraFim()
        );

        if (idIgnorado != null) {
            conflitos.removeIf(r -> r.getId().equals(idIgnorado));
        }

        if (!conflitos.isEmpty()) {
            throw new IllegalArgumentException("Já existe uma reserva conflitante para esse recurso no mesmo período.");
        }
    }
}
