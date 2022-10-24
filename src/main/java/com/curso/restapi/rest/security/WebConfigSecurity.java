package com.curso.restapi.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.curso.restapi.rest.service.ImplementacaoUserDetails;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

  @Autowired
  private ImplementacaoUserDetails details;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /* Protecao contra usuario que nao esta com token valido */
    http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        /* ativa permissao para acesso a pagina inicial */
        .disable().authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/index").permitAll()
        /* redireciona apos deslogar do sistema */
        .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
        /* mapeia url de logout e invalida usuario */
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

        /* filtra as requisicoes de login para autenticacao */
        .and()
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)
        /*
         * filtra demais requisicoes para verificar presenca do token JWT no header HTTP
         */
        .addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(details)
        .passwordEncoder(new BCryptPasswordEncoder());
  }
}
