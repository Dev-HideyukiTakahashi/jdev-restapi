package com.curso.restapi.rest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
 * JWTTokenautenticacaoService estava perdendo o contexto
 * dando conflito de null pointer exception no UsuarioRepository
 */
@Component
public class ApplicationContextLoad implements ApplicationContextAware {

  @Autowired
  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    this.applicationContext = applicationContext;

  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }
}
