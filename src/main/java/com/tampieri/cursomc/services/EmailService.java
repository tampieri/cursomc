package com.tampieri.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.tampieri.cursomc.domain.Pedido;

public interface EmailService {
	
 	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);

}
