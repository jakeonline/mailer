package com.odsinada.siteminder.service;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MailGunEmailProviderIT {

    private MailGunEmailProvider provider;

    @Before
    public void setup() {
        provider = new MailGunEmailProvider();
    }

    @Test
    public void shouldSendMail() {
        // arrange
        EmailDetails email = EmailDetails.builder()
                .from("jacob@odsinada.com")
                .to("jacob@odsinada.com")
                .to("abigail@odsinada.com")
                .cc("jake@odsinada.com")
                .bcc("jake.odsinada@gmail.com")
                .subject("Test mail - " + LocalDateTime.now())
                .body("Hello World!")
                .build();

        // act
        boolean isSent = provider.send(email);

        // assert
        assertThat(isSent, equalTo(true));

    }
}
