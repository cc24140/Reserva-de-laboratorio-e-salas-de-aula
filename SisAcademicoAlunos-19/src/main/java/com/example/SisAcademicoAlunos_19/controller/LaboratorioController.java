package com.example.SisAcademicoAlunos_19.controller;

import com.example.SisAcademicoAlunos_19.controller.dto.LaboratorioDTO;
import com.example.SisAcademicoAlunos_19.model.Laboratorio;
import com.example.SisAcademicoAlunos_19.service.LaboratorioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {    // recebe requisicoes, expoe endpoints e chama o service

    private final LaboratorioService laboratorioService;

    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @PostMapping
    public ResponseEntity<LaboratorioDTO> cadastrar(@Valid @RequestBody LaboratorioDTO dto) {
        Laboratorio laboratorio = mapearParaEntidade(dto);
        Laboratorio salvo = laboratorioService.salvar(laboratorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearParaDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<LaboratorioDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer capacidade,
            @RequestParam(required = false) String localizacao) {

        List<Laboratorio> lista = laboratorioService.buscarPorFiltros(nome, capacidade, localizacao);
        return ResponseEntity.ok(lista.stream().map(this::mapearParaDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratorioDTO> buscarPorId(@PathVariable Long id) {
        Laboratorio laboratorio = laboratorioService.buscarPorId(id);
        return ResponseEntity.ok(mapearParaDTO(laboratorio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratorioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LaboratorioDTO dto) {
        Laboratorio laboratorio = mapearParaEntidade(dto);
        Laboratorio atualizado = laboratorioService.atualizar(id, laboratorio);
        return ResponseEntity.ok(mapearParaDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        laboratorioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Laboratorio mapearParaEntidade(LaboratorioDTO dto) {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setCodigo(dto.codigo());
        laboratorio.setNome(dto.nome());
        laboratorio.setCapacidade(dto.capacidade());
        laboratorio.setLocalizacao(dto.localizacao());
        return laboratorio;
    }

    private LaboratorioDTO mapearParaDTO(Laboratorio laboratorio) {
        return new LaboratorioDTO(
                laboratorio.getId(),
                laboratorio.getCodigo(),
                laboratorio.getNome(),
                laboratorio.getCapacidade(),
                laboratorio.getLocalizacao()
        );
    }
}
