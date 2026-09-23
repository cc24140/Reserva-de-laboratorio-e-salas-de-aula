package com.example.SisAcademicoAlunos_19.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reservaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reserva de Laboratórios e Salas de Aula")
                        .description("API para cadastro de usuários, status, laboratórios, salas e reservas.")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Ambiente local"),
                        new Server().url("http://localhost:8080/api").description("API com context-path")
                ));
    }
}
