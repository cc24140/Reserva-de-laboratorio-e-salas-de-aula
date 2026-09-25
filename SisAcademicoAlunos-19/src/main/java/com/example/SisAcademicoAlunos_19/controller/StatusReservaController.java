package com.example.SisAcademicoAlunos_19.controller;

import com.example.SisAcademicoAlunos_19.controller.dto.StatusReservaDTO;
import com.example.SisAcademicoAlunos_19.model.StatusReserva;
import com.example.SisAcademicoAlunos_19.service.StatusReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status-reservas")
public class StatusReservaController {

    private final StatusReservaService statusReservaService;

    public StatusReservaController(StatusReservaService statusReservaService) {
        this.statusReservaService = statusReservaService;
    }

    @PostMapping
    public ResponseEntity<StatusReservaDTO> cadastrar(@Valid @RequestBody StatusReservaDTO dto) {
        StatusReserva statusReserva = new StatusReserva();
        statusReserva.setCodigo(dto.codigo());
        statusReserva.setNome(dto.nome());

        StatusReserva salvo = statusReservaService.salvar(statusReserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearParaDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<StatusReservaDTO>> listar(@RequestParam(required = false) String nome) {
        List<StatusReserva> lista = statusReservaService.buscarPorNome(nome);
        return ResponseEntity.ok(lista.stream().map(this::mapearParaDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusReservaDTO> buscarPorId(@PathVariable Long id) {
        StatusReserva statusReserva = statusReservaService.buscarPorId(id);
        return ResponseEntity.ok(mapearParaDTO(statusReserva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusReservaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody StatusReservaDTO dto) {
        StatusReserva statusReserva = new StatusReserva();
        statusReserva.setCodigo(dto.codigo());
        statusReserva.setNome(dto.nome());

        StatusReserva atualizado = statusReservaService.atualizar(id, statusReserva);
        return ResponseEntity.ok(mapearParaDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        statusReservaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private StatusReservaDTO mapearParaDTO(StatusReserva statusReserva) {
        return new StatusReservaDTO(statusReserva.getId(), statusReserva.getCodigo(), statusReserva.getNome());
    }
}
