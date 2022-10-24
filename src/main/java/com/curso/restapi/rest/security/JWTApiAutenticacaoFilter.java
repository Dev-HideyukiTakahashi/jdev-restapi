package com.curso.restapi.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.curso.restapi.rest.service.JWTTokenAutenticacaoService;

/*Filtro onde todas requisicoes serao capturadas para autenticar */
public class JWTApiAutenticacaoFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    /* Estabelece a autenticacao para a requisicao */
    Authentication authentication = new JWTTokenAutenticacaoService()
        .getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

    /* coloca o processe de autenticacao no spring security */
    SecurityContextHolder.getContext().setAuthentication(authentication);

    /* Continua o processo */
    chain.doFilter(request, response);

  }

}
