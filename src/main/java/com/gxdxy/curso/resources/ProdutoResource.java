package com.gxdxy.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gxdxy.curso.domain.Produto;
import com.gxdxy.curso.dto.CategoriaDTO;
import com.gxdxy.curso.dto.ProdutoDTO;
import com.gxdxy.curso.service.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> listar(@PathVariable Integer id) {
		Produto obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> listarPagina(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") Integer categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="lines", defaultValue="24") Integer lines, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="dir", defaultValue="ASC") String dir) {
		Page<Produto> lista = service.buscarProdutos(page,lines,orderBy,dir);
		Page<CategoriaDTO> listDTO = lista.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
}


