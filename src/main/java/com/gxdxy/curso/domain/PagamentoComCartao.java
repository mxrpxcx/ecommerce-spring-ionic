package com.gxdxy.curso.domain;

import java.util.Date;

import com.gxdxy.curso.domain.enums.EstadoPagamento;

public class PagamentoComCartao extends Pagamento {

	private static final long serialVersionUID = 1L;
	
	private Integer numeroParcelas;
	
	public PagamentoComCartao() {
			
		}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido,
			Integer numeroParcelas) {
		super(id, estado, pedido);
		
		this.numeroParcelas = numeroParcelas;
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	
	
	
}
