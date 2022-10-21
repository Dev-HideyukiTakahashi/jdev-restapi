package com.curso.restapi.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.restapi.rest.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
