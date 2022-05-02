package com.gxdxy.curso.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gxdxy.curso.domain.Cidade;
import com.gxdxy.curso.domain.Estado;
import com.gxdxy.curso.dto.CidadeDTO;
import com.gxdxy.curso.dto.EstadoDTO;
import com.gxdxy.curso.service.CidadeService;
import com.gxdxy.curso.service.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> buscarTodos(){
		List<Estado> estados = service.buscarTodos();
		
		List<EstadoDTO> estadosDTO = estados.stream().map(obj -> new EstadoDTO(obj))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(estadosDTO);
				
	}
	
	@RequestMapping(value="/{idEstado}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> buscarCidades(@PathVariable Integer idEstado){
		List<Cidade> cidades = cidadeService.findByEstado(idEstado);
		List<CidadeDTO> cidadesDTO = cidades.stream().map(obj->new CidadeDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadesDTO);
	}
			
}
