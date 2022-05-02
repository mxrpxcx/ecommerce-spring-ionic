package com.gxdxy.curso.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxdxy.curso.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager;
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		
		try {
		CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
				creds.getSenha(), new ArrayList<>());
		
		Authentication auth = authManager.authenticate(authToken);
		return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException,
	ServletException {
		String email = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(email);
		res.addHeader("Authorization", "Bearer "+ token);
		res.addHeader("access-control-expose-headers", "Authorization");

	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler{
		
		@Override
		public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, 
				AuthenticationException e) throws IOException, ServletException {
			res.setStatus(401);
			res.setContentType("application/json");
			res.getWriter().append(json());
		}
		
		private String json() {
			long date = new Date().getTime();
			return "{\"timestamp\": "+date+", "
					+"\"status\": 401, "
					+"\"error\": \"Não autorizado\", "
					+"\"message\": \"Email e/ou senha inválido(s)\", "
					+"\"path\": \"/login\"}";
		}
		
	}
	
}
