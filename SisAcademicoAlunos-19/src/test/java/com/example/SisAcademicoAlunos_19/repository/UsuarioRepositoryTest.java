package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678901");
        usuario.setNome("Maria Silva");
        usuario.setDataAniversario(LocalDate.of(1999, 5, 10));
        usuario.setCelular("11999999999");
        usuario.setEmail("maria.silva.teste@email.com");
        usuario.setLogin("mariaTeste");
        usuario.setSenhaHash("senha123");

        Usuario salvo = usuarioRepository.save(usuario);
        System.out.println("Dados do USUÁRIO salvo: " + salvo);
    }

    @Test
    void deveBuscarUsuarioPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setCpf("11111111111");
        usuario.setNome("Joao Souza");
        usuario.setDataAniversario(LocalDate.of(1998, 8, 15));
        usuario.setCelular("11888888888");
        usuario.setEmail("joao.souza.teste@email.com");
        usuario.setLogin("joaoTeste");
        usuario.setSenhaHash("senha123");

        usuarioRepository.save(usuario);
        var resultado = usuarioRepository.findByEmail("joao.souza.teste@email.com");
        System.out.println("Resultado da busca por email: " + resultado);
    }
}
