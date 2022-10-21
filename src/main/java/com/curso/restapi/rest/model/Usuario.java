package com.curso.restapi.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String login;
  private String senha;
  private String nome;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
  private List<Telefone> telefones = new ArrayList<>();
}
