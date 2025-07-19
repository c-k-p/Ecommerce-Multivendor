package com.snecha.service;

import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender javaMailsender;
	
	   public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) throws MessagingException {
		   
		   try {
			   MimeMessage mimeMessage = javaMailsender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text);
			mimeMessageHelper.setTo(userEmail);
			javaMailsender.send(mimeMessage);
			
			
			
			
		} catch (MailSendException e) {
			// TODO: handle exception
			throw new MailSendException("Failed to send email") ;
		}
		   
		   
		   
	   }

}
