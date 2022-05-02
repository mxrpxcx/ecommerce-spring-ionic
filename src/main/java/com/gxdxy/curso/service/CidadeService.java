package com.gxdxy.curso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Cidade;
import com.gxdxy.curso.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer idEstado){
		return repo.buscarTodos(idEstado);
	}
	
}
