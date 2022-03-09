package com.gxdxy.curso.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Cidade;
import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.domain.Endereco;
import com.gxdxy.curso.domain.enums.TipoCliente;
import com.gxdxy.curso.dto.ClienteDTO;
import com.gxdxy.curso.dto.ClienteNewDTO;
import com.gxdxy.curso.repository.ClienteRepository;
import com.gxdxy.curso.repository.EnderecoRepository;
import com.gxdxy.curso.service.exception.DataIntegrityException;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository endRepo;
	
	public Cliente buscar(Integer id){
			
		  	Optional<Cliente> obj = repo.findById(id); 
		  	return obj.orElseThrow(()->new ObjectNotFoundException(
		  			"Objeto não encontrado, ID: "+id+", TIPO: "+Cliente.class.getName()));
		  	
		  }
	
	@Transactional
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		endRepo.saveAll(obj.getEnderecos());
		return obj;
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
		return new Cliente(dto.getId(),dto.getNome(),dto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente obj = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getDocumento(), 
				TipoCliente.toEnum(dto.getTipo()),pe.encode(dto.getSenha()));
		
		
		Cidade cid = new Cidade(dto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, dto.getLogradouro(),dto.getNumero(), dto.getComplemento(),
				dto.getBairro(),dto.getCep(), obj, cid);
		obj.getEnderecos().add(end);
		obj.getTelefones().add(dto.getTelefone());
		if(dto.getTelefone2()!=null) {
			obj.getTelefones().add(dto.getTelefone2());
		}
		
		return obj;
	}
	
	private void atualizarDados(Cliente nObj, Cliente obj) {
		nObj.setNome(obj.getNome());
		nObj.setEmail(obj.getEmail());
	}
	
}
