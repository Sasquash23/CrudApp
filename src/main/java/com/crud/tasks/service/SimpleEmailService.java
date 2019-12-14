package com.crud.tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(final String reciverEmail, final String subject, final String message) {

        LOGGER.info("Starting email preparation...");

        try {
            SimpleMailMessage mailMessage = createMailMessage(reciverEmail, subject, message);
            javaMailSender.send(mailMessage);

            LOGGER.info("Email has been sent.");

        } catch (MailException e) {
            LOGGER.error("Failed to porcess email sending: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(final String reciverEmail, final String subject,
                                                final String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(reciverEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        return mailMessage;
    }
}
