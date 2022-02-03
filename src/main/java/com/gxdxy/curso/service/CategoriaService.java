package com.gxdxy.curso.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Categoria;
import com.gxdxy.curso.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id){
			
		  	Optional<Categoria> obj = repo.findById(id); 
		  	return obj.orElse(null);
		  	
		  }
	
}
