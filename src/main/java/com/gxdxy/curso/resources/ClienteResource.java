package com.gxdxy.curso.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.dto.ClienteDTO;
import com.gxdxy.curso.dto.ClienteNewDTO;
import com.gxdxy.curso.service.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> listar(@PathVariable Integer id) {
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNewDTO dto) {
		Cliente obj = service.fromDTO(dto);
		
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@PathVariable Integer id, @Valid @RequestBody ClienteDTO dto) {
		Cliente obj = service.fromDTO(dto);
		obj.setId(id);
		obj = service.atualizar(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable Integer id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listarTudo() {
		List<Cliente> lista = service.buscarTudo();
		List<ClienteDTO> listDTO = lista.stream()
				.map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> listarPagina(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="lines", defaultValue="24") Integer lines, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="dir", defaultValue="ASC") String dir) {
		Page<Cliente> lista = service.buscarPagina(page,lines,orderBy,dir);
		Page<ClienteDTO> listDTO = lista.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
}


