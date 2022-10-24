package com.curso.restapi.rest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Data
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String login;
  private String senha;
  private String nome;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
  private List<Telefone> telefones = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "usuario_role", uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id",
      "role_id" }, name = "unique_role_user"), joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role"))
  private List<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
