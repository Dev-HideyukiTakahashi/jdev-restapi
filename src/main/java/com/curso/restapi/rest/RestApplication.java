package com.curso.restapi.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
public class RestApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

	// WebMvcConfigurer
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// registry.addMapping("/**"); libera acesso a todos controllers - endpoints

		// registry.addMapping("/usuarios/**").allowedMethods("GET", "PUT");
		// libera acesso a todos endpoints GET e PUT do controller usuarios, poderia
		// liberar a origins especifica tambem com mais uma chamada de metodos

		registry.addMapping("/usuarios/**"); // libera acesso a todos endpoints do controller usuarios

		// Criptografando a senha
		// System.out.println(new BCryptPasswordEncoder().encode("admin"));
	}

}
