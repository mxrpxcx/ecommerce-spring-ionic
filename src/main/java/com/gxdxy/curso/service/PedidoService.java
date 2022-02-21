package com.gxdxy.curso.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Pedido;
import com.gxdxy.curso.repository.PedidoRepository;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id){
			
		  	Optional<Pedido> obj = repo.findById(id); 
		  	return obj.orElseThrow(()->new ObjectNotFoundException(
		  			"Objeto não encontrado, ID: "+id+", TIPO: "+Pedido.class.getName()));
		  	
		  }
	
}
