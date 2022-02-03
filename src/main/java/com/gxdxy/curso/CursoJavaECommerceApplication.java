package com.gxdxy.curso;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gxdxy.curso.domain.Categoria;
import com.gxdxy.curso.domain.Produto;
import com.gxdxy.curso.repository.CategoriaRepository;
import com.gxdxy.curso.repository.ProdutoRepository;

@SpringBootApplication
public class CursoJavaECommerceApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoJavaECommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria c1 = new Categoria(null, "Galaxy");
		Categoria c2 = new Categoria(null, "Apple");
		Categoria c3 = new Categoria(null, "Xiaomi");
		
		Produto p1 = new Produto(null, "S20 FE", 1900.00); 
		Produto p2 = new Produto(null, "iPhone SE", 2500.00);
		Produto p3 = new Produto(null, "Mi 11", 3500.00);
		
		c1.getProdutos().add(p1);
		c2.getProdutos().add(p2);
		c3.getProdutos().add(p3);
		
		p1.getCategorias().add(c1);
		p2.getCategorias().add(c2);
		p3.getCategorias().add(c3);
		
		categoriaRepo.saveAll(Arrays.asList(c1,c2, c3));
		produtoRepo.saveAll(Arrays.asList(p1,p2,p3));
		
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
