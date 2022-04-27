package com.gxdxy.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gxdxy.curso.service.S3Service;

@SpringBootApplication
public class CursoJavaECommerceApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service; 

	
	public static void main(String[] args) {
		SpringApplication.run(CursoJavaECommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		s3Service.uploadFile("C:\\Temp\\dog.jpg");
		
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
