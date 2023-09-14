package com.api.vaccinationmanagement.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendMail(String toEmail, String title, String content) throws MessagingException;
}
