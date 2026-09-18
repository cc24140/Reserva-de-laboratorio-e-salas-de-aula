package com.example.SisAcademicoAlunos_19.controller;

import com.example.SisAcademicoAlunos_19.controller.dto.*;
import com.example.SisAcademicoAlunos_19.model.Usuario;
import com.example.SisAcademicoAlunos_19.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {    // recebe requisicoes, expoe endpoints e chama o service

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> cadastrar(@Valid @RequestBody UsuarioCadastroDTO dto) {
        Usuario usuario = mapearParaEntidade(dto);
        Usuario salvo = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearParaResposta(salvo));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Usuario usuario = usuarioService.autenticar(dto.login(), dto.senha());
        return ResponseEntity.ok(new LoginRespostaDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                "Login realizado com sucesso."
        ));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRespostaDTO>> listar(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate dataAniversario) {

        List<Usuario> usuarios;
        if (email != null && !email.isBlank()) {
            usuarios = usuarioService.buscarPorEmail(email);
        } else if (dataAniversario != null) {
            usuarios = usuarioService.buscarPorDataAniversario(dataAniversario);
        } else {
            usuarios = usuarioService.listarTodos();
        }

        return ResponseEntity.ok(usuarios.stream().map(this::mapearParaResposta).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(mapearParaResposta(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody UsuarioCadastroDTO dto) {
        Usuario usuario = mapearParaEntidade(dto);
        Usuario atualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok(mapearParaResposta(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Usuario mapearParaEntidade(UsuarioCadastroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataAniversario(dto.dataAniversario());
        usuario.setCelular(dto.celular());
        usuario.setEmail(dto.email());
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(dto.senha());
        return usuario;
    }

    private UsuarioRespostaDTO mapearParaResposta(Usuario usuario) {
        return new UsuarioRespostaDTO(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getDataAniversario(),
                usuario.getCelular(),
                usuario.getEmail(),
                usuario.getLogin()
        );
    }
}
