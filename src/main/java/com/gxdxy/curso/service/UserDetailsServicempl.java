package com.gxdxy.curso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.repository.ClienteRepository;
import com.gxdxy.curso.security.UserSS;

public class UserDetailsServicempl implements UserDetailsService {

	@Autowired
	ClienteRepository cliRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli  = cliRepo.findByEmail(email);
			if(cli == null) {
				throw new UsernameNotFoundException(email);
			}
			
		return new UserSS(cli.getId(),cli.getEmail(),cli.getSenha(),cli.getPerfis());
	}

}
