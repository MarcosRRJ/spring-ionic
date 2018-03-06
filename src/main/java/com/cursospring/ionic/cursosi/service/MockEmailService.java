package com.cursospring.ionic.cursosi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.cursospring.ionic.cursosi.serviceimpl.AbstractEmailService;


public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class); 
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {

		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
	}

}
