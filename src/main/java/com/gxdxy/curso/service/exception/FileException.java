package com.gxdxy.curso.service.exception;

public class FileException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FileException(String excecao) {
		super(excecao);
	}

	public FileException(String excecao, Throwable causa) {
		super(excecao, causa);
	}
	
}
