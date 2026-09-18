package com.example.SisAcademicoAlunos_19.service;

import com.example.SisAcademicoAlunos_19.model.Usuario;
import com.example.SisAcademicoAlunos_19.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {   // chama o repository

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        validarDadosDuplicados(usuario);
        usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);

        if (!usuarioExistente.getCpf().equals(usuarioAtualizado.getCpf()) && usuarioRepository.findByCpf(usuarioAtualizado.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema.");
        }

        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail()) && !usuarioRepository.findByEmail(usuarioAtualizado.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("Email já cadastrado no sistema.");
        }

        if (!usuarioExistente.getLogin().equals(usuarioAtualizado.getLogin()) && usuarioRepository.findByLogin(usuarioAtualizado.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login já cadastrado no sistema.");
        }

        usuarioAtualizado.setId(id);

        if (usuarioAtualizado.getSenhaHash() != null && !usuarioAtualizado.getSenhaHash().isBlank()) {
            usuarioAtualizado.setSenhaHash(passwordEncoder.encode(usuarioAtualizado.getSenhaHash()));
        } else {
            usuarioAtualizado.setSenhaHash(usuarioExistente.getSenhaHash());
        }

        return usuarioRepository.save(usuarioAtualizado);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return listarTodos();
        }
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> buscarPorDataAniversario(LocalDate data) {
        if (data == null) {
            return listarTodos();
        }
        return usuarioRepository.findByDataAniversario(data);
    }

    public Usuario autenticar(String login, String senha) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Login ou senha inválidos."));

        if (!passwordEncoder.matches(senha, usuario.getSenhaHash())) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }

        return usuario;
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }

    private void validarDadosDuplicados(Usuario usuario) {
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema.");
        }

        if (!usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("Email já cadastrado no sistema.");
        }

        if (usuarioRepository.findByLogin(usuario.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login já cadastrado no sistema.");
        }
    }
}
