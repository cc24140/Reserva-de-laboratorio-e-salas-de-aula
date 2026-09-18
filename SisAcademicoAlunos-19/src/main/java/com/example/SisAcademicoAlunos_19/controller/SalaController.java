package com.example.SisAcademicoAlunos_19.controller;

import com.example.SisAcademicoAlunos_19.controller.dto.SalaDTO;
import com.example.SisAcademicoAlunos_19.model.Sala;
import com.example.SisAcademicoAlunos_19.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {   // recebe requisicoes, expoe endpoints e chama o service

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<SalaDTO> cadastrar(@Valid @RequestBody SalaDTO dto) {
        Sala sala = mapearParaEntidade(dto);
        Sala salvo = salaService.salvar(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearParaDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<SalaDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer capacidade,
            @RequestParam(required = false) String localizacao) {

        List<Sala> lista = salaService.buscarPorFiltros(nome, capacidade, localizacao);
        return ResponseEntity.ok(lista.stream().map(this::mapearParaDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarPorId(@PathVariable Long id) {
        Sala sala = salaService.buscarPorId(id);
        return ResponseEntity.ok(mapearParaDTO(sala));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody SalaDTO dto) {
        Sala sala = mapearParaEntidade(dto);
        Sala atualizado = salaService.atualizar(id, sala);
        return ResponseEntity.ok(mapearParaDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        salaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Sala mapearParaEntidade(SalaDTO dto) {
        Sala sala = new Sala();
        sala.setCodigo(dto.codigo());
        sala.setNome(dto.nome());
        sala.setCapacidade(dto.capacidade());
        sala.setLocalizacao(dto.localizacao());
        return sala;
    }

    private SalaDTO mapearParaDTO(Sala sala) {
        return new SalaDTO(
                sala.getId(),
                sala.getCodigo(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.getLocalizacao()
        );
    }
}
