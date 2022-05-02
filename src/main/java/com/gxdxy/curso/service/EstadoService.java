package com.gxdxy.curso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Estado;
import com.gxdxy.curso.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> buscarTodos(){
		return repo.findAllByOrderByNome();
	}
	
}
