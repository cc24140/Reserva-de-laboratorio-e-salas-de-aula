package com.example.SisAcademicoAlunos_19.repository;

import com.example.SisAcademicoAlunos_19.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Test
    void deveSalvarReserva() {
        Usuario usuario = new Usuario();
        usuario.setCpf("22222222222");
        usuario.setNome("Carlos Almeida");
        usuario.setDataAniversario(LocalDate.of(1995, 4, 20));
        usuario.setCelular("11977777777");
        usuario.setEmail("carlos.almeida.teste@email.com");
        usuario.setLogin("carlosTeste");
        usuario.setSenhaHash("senha123");
        usuario = usuarioRepository.save(usuario);

        Status status = new Status();
        status.setCodigo("ST_RES_001");
        status.setNome("Reservado");
        status = statusRepository.save(status);

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setCodigo("LAB_RES_001");
        laboratorio.setNome("Laboratório de Testes");
        laboratorio.setCapacidade(15);
        laboratorio.setLocalizacao("Bloco E");
        laboratorio = laboratorioRepository.save(laboratorio);

        Reserva reserva = new Reserva();
        reserva.setDataInicio(LocalDate.of(2026, 10, 10));
        reserva.setDataFim(LocalDate.of(2026, 10, 10));
        reserva.setHoraInicio(LocalTime.of(9, 0));
        reserva.setHoraFim(LocalTime.of(11, 0));
        reserva.setUsuario(usuario);
        reserva.setLaboratorio(laboratorio);
        reserva.setStatus(status);

        Reserva salva = reservaRepository.save(reserva);
        System.out.println("Dados da RESERVA salva: " + salva);
    }

    @Test
    void deveBuscarReservasPorUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCpf("33333333333");
        usuario.setNome("Ana Paula");
        usuario.setDataAniversario(LocalDate.of(2001, 6, 5));
        usuario.setCelular("11966666666");
        usuario.setEmail("ana.paula.teste@email.com");
        usuario.setLogin("anaTeste");
        usuario.setSenhaHash("senha123");
        usuario = usuarioRepository.save(usuario);

        Status status = new Status();
        status.setCodigo("ST_RES_002");
        status.setNome("Livre");
        status = statusRepository.save(status);

        Sala sala = new Sala();
        sala.setCodigo("SAL_RES_001");
        sala.setNome("Sala de Treinamento");
        sala.setCapacidade(12);
        sala.setLocalizacao("Bloco F");
        sala = salaRepository.save(sala);

        Reserva reserva = new Reserva();
        reserva.setDataInicio(LocalDate.of(2026, 10, 12));
        reserva.setDataFim(LocalDate.of(2026, 10, 12));
        reserva.setHoraInicio(LocalTime.of(14, 0));
        reserva.setHoraFim(LocalTime.of(16, 0));
        reserva.setUsuario(usuario);
        reserva.setSala(sala);
        reserva.setStatus(status);

        reservaRepository.save(reserva);
        var resultado = reservaRepository.findByUsuario_Id(usuario.getId());
        System.out.println("Resultado da busca por usuário: " + resultado);
    }
}
