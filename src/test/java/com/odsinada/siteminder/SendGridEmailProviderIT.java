package com.odsinada.siteminder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SendGridEmailProviderIT {

    private SendGridEmailProvider provider;

    @Before
    public void setup() {
        provider = new SendGridEmailProvider(new ObjectMapper());
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
                .subject("SendGrip test - " + LocalDateTime.now())
                .body("SendGrip API hello!")
                .build();

        // act
        boolean isSent = provider.send(email);

        // assert
        assertThat(isSent, equalTo(true));

    }
}