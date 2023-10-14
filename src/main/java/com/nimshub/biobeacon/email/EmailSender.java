package com.nimshub.biobeacon.email;

public interface EmailSender {
    void send(String receiver, String subject, String body);
}
