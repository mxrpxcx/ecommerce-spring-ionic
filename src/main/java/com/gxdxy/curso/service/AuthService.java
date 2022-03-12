package com.gxdxy.curso.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.repository.ClienteRepository;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository repoCli;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cli = repoCli.findByEmail(email);
		
		if(cli==null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cli.setSenha(pe.encode(newPass));
			
		repoCli.save(cli);
		emailService.sendNewPasswordEmail(cli, newPass);
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0;i<10;i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		if(opt==0) { //gera digito
			return (char) (rand.nextInt(10)+48);
		}else if(opt==1) { //gera maiuscula
			return (char) (rand.nextInt(26)+65);
		} else {//minuscula
			return (char) (rand.nextInt(26)+97);
		}
	}
	
}
