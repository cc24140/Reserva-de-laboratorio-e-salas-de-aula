package com.example.SisAcademicoAlunos_19.controller.commom;

import com.example.SisAcademicoAlunos_19.controller.dto.ErroCampo;
import com.example.SisAcademicoAlunos_19.controller.dto.ErroResposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
   private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
   public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
   {
       List<FieldError> fieldErrors = e.getFieldErrors();
       List<ErroCampo> listaErros = fieldErrors
               .stream()
               .map(fe -> new ErroCampo(fe.getField(),fe.getDefaultMessage()))
               .collect(Collectors.toList());
       return ErroResposta.validacao(listaErros);
   }

   @ExceptionHandler(NoHandlerFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ErroResposta handleNoHandlerFound(NoHandlerFoundException e) {
       return ErroResposta.notFound("Recurso não encontrado");
   }

   @ExceptionHandler({HttpMessageNotReadableException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErroResposta handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
       // Log full error for debugging
       logger.warn("JSON inválido ou malformado: {}", e.getMessage());
       return ErroResposta.badRequest("JSON inválido ou malformado");
   }

   @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
   @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
   public ErroResposta handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
       logger.warn("Tipo de mídia não suportado: {}", e.getContentType());
       return ErroResposta.badRequest("Tipo de mídia não suportado");
   }

   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErroResposta handleTypeMismatch(MethodArgumentTypeMismatchException e) {
       String msg = String.format("Parâmetro '%s' com tipo inválido", e.getName());
       return ErroResposta.badRequest(msg);
   }

   @ExceptionHandler(MissingServletRequestParameterException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErroResposta handleMissingParams(MissingServletRequestParameterException e) {
       String msg = "Parâmetro obrigatório ausente: " + e.getParameterName();
       return ErroResposta.badRequest(msg);
   }

   @ExceptionHandler(DataIntegrityViolationException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   public ErroResposta handleDataIntegrity(DataIntegrityViolationException e) {
       logger.warn("Violação de integridade de dados: {}", e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : e.getMessage());
       return ErroResposta.conflito("Violação de integridade de dados");
   }

   @ExceptionHandler(IllegalArgumentException.class)
   @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
   public ErroResposta handleIllegalArgumentException(IllegalArgumentException e) {
       // mensagens lançadas manualmente em serviços são apresentadas ao usuário
       return ErroResposta.respostaPadrao(e.getMessage());
   }

   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ErroResposta handleGenericException(Exception e) {
       // Log exception details server-side, but return generic message to client
       logger.error("Erro interno no servidor", e);
       return ErroResposta.internal();
   }
}
