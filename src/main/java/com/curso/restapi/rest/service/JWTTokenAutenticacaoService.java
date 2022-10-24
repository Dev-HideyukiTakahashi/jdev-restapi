package com.curso.restapi.rest.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.restapi.rest.ApplicationContextLoad;
import com.curso.restapi.rest.model.Usuario;
import com.curso.restapi.rest.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional
public class JWTTokenAutenticacaoService {

  /* Tempo de validade do Token, 2 dias nesse caso(milisegundos) */
  private static final long EXPIRATION_TIME = 172800000;

  /* Senha unica para compor a autenticacao */
  private static final String SECRET = "SenhaExtremamenteSecreta";

  /* Prefixo padrao de Token */
  private static final String TOKEN_PREFIX = "Bearer";

  private static final String HEADER_STRING = "Authorization";

  /* Gerando token de autenticacao e adicionando ao cabecalho e resposta http */
  public void addAuthentication(HttpServletResponse response, String username) throws IOException {

    /* Montando o Token */
    String JWT = Jwts.builder()
        .setSubject(username) // adiciona o usuario
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // tempo de expiracao
        .signWith(SignatureAlgorithm.HS512, SECRET).compact(); // algoritmo de geracao de senha e compactacao

    String token = TOKEN_PREFIX + " " + JWT; // EX: Bearer fnjk383nfdf3m2m3kmkf3efsdf

    /* adiciona no cabecalho http */
    response.addHeader(HEADER_STRING, token);//// EX: Authorization: Bearer fnjk383nfdf3m2m3kmkf3efsdf

    /* token como responsta no corpo http */
    response.getWriter().write("{\"Authorization\":\"" + token + "\"}");
  }

  /* retorna o usuario validado com token, ou retorna null */
  public Authentication getAuthentication(HttpServletRequest request) {
    /* pega o token enviado no cabecalho http */
    String token = request.getHeader(HEADER_STRING);

    if (token != null) {
      /* faz a validacao do token do usuario na requisicao */
      String user = Jwts.parser()
          .setSigningKey(SECRET) // EX: Bearer fnjk383nfdf3m2m3kmkf3efsdf
          .parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // EX: fnjk383nfdf3m2m3kmkf3efsdf
          .getBody().getSubject(); // Usuario Nome

      if (user != null) {
        Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
            .findByLogin(user);
        if (usuario != null) {
          return new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(),
              usuario.getAuthorities());
        }
      }
    }

    return null; // nao autorizado
  }

}
