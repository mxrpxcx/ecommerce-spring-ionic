package com.gxdxy.curso.service.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String excecao) {
		super(excecao);
	}

	public ObjectNotFoundException(String excecao, Throwable causa) {
		super(excecao, causa);
	}
	
}
