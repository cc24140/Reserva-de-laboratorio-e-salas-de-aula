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

   // serve para capturar exceções de validação de argumentos de método e retornar uma resposta personalizada
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

   // serve para capturar exceções de recurso não encontrado e retornar uma resposta personalizada
   @ExceptionHandler(NoHandlerFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ErroResposta handleNoHandlerFound(NoHandlerFoundException e) {
       return ErroResposta.notFound("Recurso não encontrado");
   }

   // serve para capturar exceções de mensagem HTTP não legível (como JSON malformado) e retornar uma resposta personalizada
   @ExceptionHandler({HttpMessageNotReadableException.class})
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErroResposta handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
       logger.warn("JSON inválido ou malformado: {}", e.getMessage());
       return ErroResposta.badRequest("JSON inválido ou malformado");
   }

   // serve para capturar exceções de tipo de mídia não suportado e retornar uma resposta personalizada
   @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
   @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
   public ErroResposta handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
       logger.warn("Tipo de mídia não suportado: {}", e.getContentType());
       return ErroResposta.badRequest("Tipo de mídia não suportado");
   }

   // serve para capturar exceções de incompatibilidade de tipo de argumento de método e retornar uma resposta personalizada
   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErroResposta handleTypeMismatch(MethodArgumentTypeMismatchException e) {
       String msg = String.format("Parâmetro '%s' com tipo inválido", e.getName());
       return ErroResposta.badRequest(msg);
   }

   // serve para capturar exceções de parâmetros de requisição ausentes e retornar uma resposta personalizada
   @ExceptionHandler(MissingServletRequestParameterException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErroResposta handleMissingParams(MissingServletRequestParameterException e) {
       String msg = "Parâmetro obrigatório ausente: " + e.getParameterName();
       return ErroResposta.badRequest(msg);
   }

   // serve para capturar exceções de violação de integridade de dados (como chaves duplicadas) e retornar uma resposta personalizada
   @ExceptionHandler(DataIntegrityViolationException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   public ErroResposta handleDataIntegrity(DataIntegrityViolationException e) {
       logger.warn("Violação de integridade de dados: {}", e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : e.getMessage());
       return ErroResposta.conflito("Violação de integridade de dados");
   }

   // serve para capturar exceções de argumento ilegal e retornar uma resposta personalizada
   @ExceptionHandler(IllegalArgumentException.class)
   @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
   public ErroResposta handleIllegalArgumentException(IllegalArgumentException e) {
       return ErroResposta.respostaPadrao(e.getMessage());
   }

   // serve para capturar quaisquer outras exceções não tratadas e retornar uma resposta genérica de erro interno do servidor
   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ErroResposta handleGenericException(Exception e) {
       logger.error("Erro interno no servidor", e);
       return ErroResposta.internal();
   }
}
