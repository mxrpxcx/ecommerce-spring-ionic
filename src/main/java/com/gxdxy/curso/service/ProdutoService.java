package com.gxdxy.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Categoria;
import com.gxdxy.curso.domain.Produto;
import com.gxdxy.curso.repository.CategoriaRepository;
import com.gxdxy.curso.repository.ProdutoRepository;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository repoCat;
	
	public Produto buscar(Integer id){
			
		  	Optional<Produto> obj = repo.findById(id); 
		  	return obj.orElseThrow(()->new ObjectNotFoundException(
		  			"Objeto não encontrado, ID: "+id+", TIPO: "+Produto.class.getName()));
		  	
		  }
	
	public Page<Produto> buscarProdutos(String nome, List<Integer> ids, Integer page, 
			Integer lines, String orderBy, String dir){
		PageRequest pageRequest = PageRequest.of(page, lines, Direction.valueOf(dir), orderBy);
		List<Categoria> categorias = repoCat.findAllById(ids);
		
		return repo.buscar(nome, categorias, pageRequest);
	}
	
}
