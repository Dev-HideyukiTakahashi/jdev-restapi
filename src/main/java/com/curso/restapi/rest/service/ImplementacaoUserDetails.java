package com.curso.restapi.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.restapi.rest.model.Usuario;
import com.curso.restapi.rest.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementacaoUserDetails implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByLogin(username);

    if (usuario == null) {
      throw new UsernameNotFoundException("Usuario não encontrado");
    }

    return new User(usuario.getUsername(), usuario.getPassword(), usuario.getRoles());
  }

}
