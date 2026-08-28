package com.example.SisAcademicoAlunos_19.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException
{
    public OperacaoNaoPermitidaException(String mensagem)
    {
        super(mensagem);
    }
}
