package com.gxdxy.curso.service;

import org.springframework.mail.SimpleMailMessage;

import com.gxdxy.curso.domain.Pedido;

public interface EmailService {

	void enviarConfirmacaoPedido(Pedido obj);
	void enviarEmail(SimpleMailMessage msg);
	
}
