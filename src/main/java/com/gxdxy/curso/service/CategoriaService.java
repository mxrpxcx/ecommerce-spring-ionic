package com.gxdxy.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Categoria;
import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.dto.CategoriaDTO;
import com.gxdxy.curso.repository.CategoriaRepository;
import com.gxdxy.curso.service.exception.DataIntegrityException;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {

		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, ID: " + id + ", TIPO: " + Categoria.class.getName()));

	}

	public Categoria inserir(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria atualizar(Categoria obj) {
		Categoria nObj = buscar(obj.getId());
		atualizarDados(nObj, obj);
		return repo.save(nObj);
	}

	public void deletar(Integer id) {
		buscar(id);
		
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> buscarTudo() {
		return repo.findAll();
	}
	
	public Page<Categoria> buscarPagina(Integer page, Integer lines, String orderBy, String dir){
		PageRequest pageRequest = PageRequest.of(page, lines, Direction.valueOf(dir), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome()); 
	}
	
	private void atualizarDados(Categoria nObj, Categoria obj) {
		nObj.setNome(obj.getNome());
	}

}
