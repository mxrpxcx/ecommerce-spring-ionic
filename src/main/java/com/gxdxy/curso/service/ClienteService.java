package com.gxdxy.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.dto.ClienteDTO;
import com.gxdxy.curso.repository.ClienteRepository;
import com.gxdxy.curso.service.exception.DataIntegrityException;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id){
			
		  	Optional<Cliente> obj = repo.findById(id); 
		  	return obj.orElseThrow(()->new ObjectNotFoundException(
		  			"Objeto não encontrado, ID: "+id+", TIPO: "+Cliente.class.getName()));
		  	
		  }
	
	public Cliente atualizar(Cliente obj) {
		Cliente nObj = buscar(obj.getId());
		atualizarDados(nObj, obj);
		return repo.save(nObj);
	}

	public void deletar(Integer id) {
		buscar(id);
		
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> buscarTudo() {
		return repo.findAll();
	}
	
	public Page<Cliente> buscarPagina(Integer page, Integer lines, String orderBy, String dir){
		PageRequest pageRequest = PageRequest.of(page, lines, Direction.valueOf(dir), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(),dto.getNome(),dto.getEmail(), null, null);
	}
	
	private void atualizarDados(Cliente nObj, Cliente obj) {
		nObj.setNome(obj.getNome());
		nObj.setEmail(obj.getEmail());
	}
	
}
