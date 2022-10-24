package com.curso.restapi.rest;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

  @Override
  @ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    ObjetoErro objetoErro = new ObjetoErro();
    String msg = "";

    if (ex instanceof MethodArgumentNotValidException) {
      List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
      for (ObjectError objectError : list) {
        msg += objectError.getDefaultMessage() + "\n";
      }
    } else {
      msg = ex.getMessage();
    }
    objetoErro.setError(msg);
    objetoErro.setCode(status.value() + " ==> " + status.getReasonPhrase());
    return new ResponseEntity<>(objetoErro, headers, status);
  }
}
