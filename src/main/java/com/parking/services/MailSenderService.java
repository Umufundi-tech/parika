package com.parking.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

	private final JavaMailSender mailSender;

	@Autowired
	public MailSenderService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendNewMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setCc("ruvumu125@gmail.com");
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

}
