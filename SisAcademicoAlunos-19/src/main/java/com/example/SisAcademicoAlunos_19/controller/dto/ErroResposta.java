package com.example.SisAcademicoAlunos_19.controller.dto;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {
    public static ErroResposta conflito(String mensagem) {
        return new ErroResposta(409, mensagem, null);
    }

    public static ErroResposta respostaPadrao(String mensagem) {
        return new ErroResposta(422, mensagem, null);
    }

    public static ErroResposta validacao(List<ErroCampo> erros) {
        return new ErroResposta(422, "Erro de validação de campos", erros);
    }

    public static ErroResposta badRequest(String mensagem) {
        return new ErroResposta(400, mensagem, null);
    }

    public static ErroResposta internal() {
        return new ErroResposta(500, "Erro interno no servidor", null);
    }

    public static ErroResposta notFound(String mensagem) {
        return new ErroResposta(404, mensagem, null);
    }
}
