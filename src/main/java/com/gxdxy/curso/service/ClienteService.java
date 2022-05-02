package com.gxdxy.curso.service;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gxdxy.curso.domain.Cidade;
import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.domain.Endereco;
import com.gxdxy.curso.domain.enums.Perfil;
import com.gxdxy.curso.domain.enums.TipoCliente;
import com.gxdxy.curso.dto.ClienteDTO;
import com.gxdxy.curso.dto.ClienteNewDTO;
import com.gxdxy.curso.repository.ClienteRepository;
import com.gxdxy.curso.repository.EnderecoRepository;
import com.gxdxy.curso.security.UserSS;
import com.gxdxy.curso.service.exception.AuthorizationException;
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
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imgService;
	
	@Value("${img.prefix.client.profile}")
	private String prefixo;
	
	@Value("${img.profile.size}")
	private Integer tamanhoImagem;
	
	
	public Cliente buscar(Integer id){
		
		UserSS user = UserService.authenticated();
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
			
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
	
	public Cliente buscar(String email) {
		UserSS user = UserService.authenticated();
		
		if(user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente obj = repo.findByEmail(email);
		
		if(obj==null) {
			throw new ObjectNotFoundException(
		  			"Objeto não encontrado, ID: "+user.getId()+", TIPO: "+Cliente.class.getName());
		}
		
		return obj;
		
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
	
	public URI uploadFoto(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		
		if(user==null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imgService.jpgFromFile(multipartFile);
		jpgImage = imgService.enquadrarImagem(jpgImage);
		jpgImage = imgService.redimensionarImagem(jpgImage, tamanhoImagem);
		
		
		String fileName = prefixo+user.getId()+".jpg";
		
		return s3Service.uploadFile(imgService.getInputStream(jpgImage, "jpg"),fileName,
				"image"); 
		
	}
	
}
