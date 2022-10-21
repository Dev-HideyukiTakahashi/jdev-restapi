package com.curso.restapi.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    Usuario usuario = new Usuario();
    usuario.setNome(nome);
    return ResponseEntity.ok(usuario);
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

  @PostMapping(path = "/")
  public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
    Usuario usuarioSalvo = usuarioRepository.save(usuario);
    return ResponseEntity.ok(usuarioSalvo);
  }

  /**
   * Mandando o id por parametro, o metodo save atualiza os dados
   * sem o id cria um novo usuario
   * 
   * @param usuario
   * @return usuario atualizado
   */
  @PutMapping(path = "/")
  public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
    Usuario usuarioSalvo = usuarioRepository.save(usuario);
    return ResponseEntity.ok(usuarioSalvo);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Usuario> deletar(@PathVariable Long id) {
    usuarioRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
