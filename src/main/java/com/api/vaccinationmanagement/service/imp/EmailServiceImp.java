package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String toEmail, String title, String content) throws MessagingException {
        String body = content;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("verifyemail.java@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(title);
        helper.setText(body, true);

        javaMailSender.send(message);
    }
}
