package com.cursospring.ionic.cursosi.service;

import org.springframework.mail.SimpleMailMessage;

import com.cursospring.ionic.cursosi.model.Pedido;

public interface EmailService {

	void sendOrderConfimationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
