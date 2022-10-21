package com.curso.restapi.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curso.restapi.rest.model.Usuario;
import com.curso.restapi.rest.repository.UsuarioRepository;

@RestController
@RequestMapping(path = "/usuarios")
public class IndexController {

  @Autowired
  private UsuarioRepository usuarioRepository;

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
  @GetMapping(path = "/ola", produces = "application/json")
  public ResponseEntity<Usuario> init(@RequestParam(value = "nome", required = false) String nome) {
    return new ResponseEntity("Olá REST Spring Boot meu nome é " + nome, HttpStatus.OK);
  }

  /**
   * Optional retorna null se nao encontrar para evitar nullpointer exception
   * 
   * @param id
   * @return usuario pelo id
   */
  @GetMapping(path = "/{id}")
  public ResponseEntity<Usuario> consulta(@PathVariable Long id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);
    return ResponseEntity.ok(usuario.get());
  }

  @GetMapping(path = "/")
  public ResponseEntity<List<Usuario>> consultaTodos() {
    List<Usuario> usuarios = usuarioRepository.findAll();
    return ResponseEntity.ok(usuarios);
  }
}
