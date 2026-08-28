package com.example.SisAcademicoAlunos_19.exceptions;

public class RegistroDuplicadoException extends RuntimeException
{
    public RegistroDuplicadoException(String mensagem)
    {
        super(mensagem);
    }
}

