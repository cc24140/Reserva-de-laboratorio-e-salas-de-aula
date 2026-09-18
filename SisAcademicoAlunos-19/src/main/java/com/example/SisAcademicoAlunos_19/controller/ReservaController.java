package com.example.SisAcademicoAlunos_19.controller;

import com.example.SisAcademicoAlunos_19.controller.dto.ReservaCadastroDTO;
import com.example.SisAcademicoAlunos_19.controller.dto.ReservaRespostaDTO;
import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import com.example.SisAcademicoAlunos_19.model.Reserva;
import com.example.SisAcademicoAlunos_19.model.Sala;
import com.example.SisAcademicoAlunos_19.model.Status;
import com.example.SisAcademicoAlunos_19.model.Usuario;
import com.example.SisAcademicoAlunos_19.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {    // recebe requisicoes, expoe endpoints e chama o service

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaRespostaDTO> cadastrar(@Valid @RequestBody ReservaCadastroDTO dto) {
        Reserva reserva = mapearParaEntidade(dto);
        Reserva salva = reservaService.salvar(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearParaResposta(salva));
    }

    @GetMapping
    public ResponseEntity<List<ReservaRespostaDTO>> listar(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long laboratorioId,
            @RequestParam(required = false) Long salaId,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        List<Reserva> reservas = reservaService.listarTodos();

        if (usuarioId != null) {
            reservas = reservaService.buscarPorUsuario(usuarioId);
        }
        if (dataInicio != null && dataFim != null) {
            reservas = reservaService.buscarPorPeriodo(dataInicio, dataFim);
        } else if (dataInicio != null) {
            reservas = reservaService.buscarPorData(dataInicio);
        }

        return ResponseEntity.ok(reservas.stream().map(this::mapearParaResposta).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaRespostaDTO> buscarPorId(@PathVariable Long id) {
        Reserva reserva = reservaService.buscarPorId(id);
        return ResponseEntity.ok(mapearParaResposta(reserva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaRespostaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ReservaCadastroDTO dto) {
        Reserva reserva = mapearParaEntidade(dto);
        Reserva atualizada = reservaService.atualizar(id, reserva);
        return ResponseEntity.ok(mapearParaResposta(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        reservaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Reserva mapearParaEntidade(ReservaCadastroDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setDataInicio(dto.dataInicio());
        reserva.setDataFim(dto.dataFim());
        reserva.setHoraInicio(dto.horaInicio());
        reserva.setHoraFim(dto.horaFim());

        Usuario usuario = new Usuario();
        usuario.setId(dto.usuarioId());
        reserva.setUsuario(usuario);

        if (dto.laboratorioId() != null) {
            Laboratorio laboratorio = new Laboratorio();
            laboratorio.setId(dto.laboratorioId());
            reserva.setLaboratorio(laboratorio);
        }

        if (dto.salaId() != null) {
            Sala sala = new Sala();
            sala.setId(dto.salaId());
            reserva.setSala(sala);
        }

        Status status = new Status();
        status.setId(dto.statusId());
        reserva.setStatus(status);

        return reserva;
    }

    private ReservaRespostaDTO mapearParaResposta(Reserva reserva) {
        Long laboratorioId = reserva.getLaboratorio() != null ? reserva.getLaboratorio().getId() : null;
        Long salaId = reserva.getSala() != null ? reserva.getSala().getId() : null;
        String nomeUsuario = reserva.getUsuario() != null ? reserva.getUsuario().getNome() : null;
        String nomeStatus = reserva.getStatus() != null ? reserva.getStatus().getNome() : null;

        return new ReservaRespostaDTO(
                reserva.getId(),
                reserva.getDataInicio(),
                reserva.getDataFim(),
                reserva.getHoraInicio(),
                reserva.getHoraFim(),
                reserva.getUsuario() != null ? reserva.getUsuario().getId() : null,
                nomeUsuario,
                laboratorioId,
                salaId,
                reserva.getStatus() != null ? reserva.getStatus().getId() : null,
                nomeStatus
        );
    }
}
