package com.curso.restapi.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  /**
   * path ou value eh o endpoint(endereco) para acessar a pagina
   * por padrao, a troca de dados eh por json
   * Requestparam: required 'false' retorna 'null'
   * Por padrao o required eh true e gera uma excecao se acessar o endpoint
   * sem informar esse parametro
   * Requestparam pode receber um valor default tambem
   * 
   * @param nome recebe o parametro pela requisicao web
   * @return
   */
  @GetMapping(path = "/", produces = "application/json")
  public ResponseEntity init(@RequestParam(value = "nome", required = false) String nome) {
    return new ResponseEntity("Olá REST Spring Boot meu nome é " + nome, HttpStatus.OK);
  }
}
