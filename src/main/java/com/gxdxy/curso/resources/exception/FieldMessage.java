package com.gxdxy.curso.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nomeCampo;
	private String mensagem;
	
	public FieldMessage() {
		
	}

	public FieldMessage(String nomeCampo, String mensagem) {
		super();
		this.nomeCampo = nomeCampo;
		this.mensagem = mensagem;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
