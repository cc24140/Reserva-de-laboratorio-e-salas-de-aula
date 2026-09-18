package com.example.SisAcademicoAlunos_19.controller;

import com.example.SisAcademicoAlunos_19.controller.dto.StatusDTO;
import com.example.SisAcademicoAlunos_19.model.Status;
import com.example.SisAcademicoAlunos_19.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {  // recebe requisicoes, expoe endpoints e chama o service

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<StatusDTO> cadastrar(@Valid @RequestBody StatusDTO dto) {
        Status status = new Status();
        status.setCodigo(dto.codigo());
        status.setNome(dto.nome());

        Status salvo = statusService.salvar(status);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearParaDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<StatusDTO>> listar(@RequestParam(required = false) String nome) {
        List<Status> lista = statusService.buscarPorNome(nome);
        return ResponseEntity.ok(lista.stream().map(this::mapearParaDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> buscarPorId(@PathVariable Long id) {
        Status status = statusService.buscarPorId(id);
        return ResponseEntity.ok(mapearParaDTO(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusDTO> atualizar(@PathVariable Long id, @Valid @RequestBody StatusDTO dto) {
        Status status = new Status();
        status.setCodigo(dto.codigo());
        status.setNome(dto.nome());

        Status atualizado = statusService.atualizar(id, status);
        return ResponseEntity.ok(mapearParaDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        statusService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private StatusDTO mapearParaDTO(Status status) {
        return new StatusDTO(status.getId(), status.getCodigo(), status.getNome());
    }
}
