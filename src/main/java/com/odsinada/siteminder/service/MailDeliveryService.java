package com.odsinada.siteminder.service;

public interface MailDeliveryService {
    void send(EmailDetails emailDetails) throws MailDeliveryException;
}
