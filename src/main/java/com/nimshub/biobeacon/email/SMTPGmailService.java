package com.nimshub.biobeacon.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SMTPGmailService implements EmailSender{

    private final JavaMailSender mailSender;
    @Override
    public void send(String receiver, String subject, String body) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(receiver);
        email.setSubject(subject);
        email.setText(body);

        mailSender.send(email);
        System.out.println("Mail has been sent...");
    }
}
