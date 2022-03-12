package com.gxdxy.curso.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.domain.Pedido;

public interface EmailService {

	void enviarConfirmacaoPedido(Pedido obj);
	void enviarEmail(SimpleMailMessage msg);
	
	void enviarConfirmacaoPedidoHTML(Pedido obj);
	void enviarEmailHTML(MimeMessage msg);
	
	void sendNewPasswordEmail(Cliente cli, String pass);
}
