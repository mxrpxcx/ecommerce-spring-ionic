package com.gxdxy.curso.service.exception;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String excecao) {
		super(excecao);
	}

	public DataIntegrityException(String excecao, Throwable causa) {
		super(excecao, causa);
	}
	
}
