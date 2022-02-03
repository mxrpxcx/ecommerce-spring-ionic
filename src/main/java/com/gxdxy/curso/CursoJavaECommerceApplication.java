package com.gxdxy.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursoJavaECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursoJavaECommerceApplication.class, args);
	}

	/* Implementacao do find by id java 8
	 * 
	 * public Categoria find(Integer id){
	 * 
	 * 	Optional<Categoria> obj = repo.findById(id); 
	 * 	return obj.orElse(null);
	 * }
	 * 
	 */
	
}
