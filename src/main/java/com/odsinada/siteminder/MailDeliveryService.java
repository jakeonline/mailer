package com.odsinada.siteminder;

public interface MailDeliveryService {
    void send(EmailDetails emailDetails) throws MailDeliveryException;
}
