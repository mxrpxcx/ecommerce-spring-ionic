package com.gxdxy.curso.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gxdxy.curso.domain.Cliente;
import com.gxdxy.curso.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void enviarConfirmacaoPedido(Pedido obj) {
		SimpleMailMessage sm = prepararEmailPedido(obj);
		enviarEmail(sm);
	}

	protected SimpleMailMessage prepararEmailPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void enviarConfirmacaoPedidoHTML(Pedido obj) {
		try {
		MimeMessage mm = preparaMimeMessagePedido(obj);
		enviarEmailHTML(mm);
		} catch (MessagingException e) {
			enviarConfirmacaoPedido(obj);
		}
	}

	protected MimeMessage preparaMimeMessagePedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh  = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail()); 
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: "+obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cli, String pass) {
		SimpleMailMessage sm = prepararNovaSenha(cli, pass);
		enviarEmail(sm);
	}

	protected SimpleMailMessage prepararNovaSenha(Cliente cli, String pass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cli.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha "+pass);
		return sm;
	}
	
}
