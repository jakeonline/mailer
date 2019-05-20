package com.odsinada.siteminder;

public interface EmailProvider {

    boolean send(EmailDetails email);
}
