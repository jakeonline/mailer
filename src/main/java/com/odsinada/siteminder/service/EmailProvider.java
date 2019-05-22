package com.odsinada.siteminder.service;

public interface EmailProvider {

    boolean send(EmailDetails email);
}
