package com.gxdxy.curso.service.exception;

public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String excecao) {
		super(excecao);
	}

	public AuthorizationException(String excecao, Throwable causa) {
		super(excecao, causa);
	}
	
}
