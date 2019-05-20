package com.odsinada.siteminder;

import org.junit.Before;
import org.junit.Test;

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
                .from("MailGun <mailgun@sandbox16ae66a08f7b486eb121269181144231.mailgun.org>")
                .to("jacob@odsinada.com")
                .to("abigail@odsinada.com")
                .cc("jake@odsinada.com")
                .bcc("jake.odsinada@gmail.com")
                .subject("Test mail 111")
                .body("Hello World!")
                .build();

        // act
        boolean isSent = provider.send(email);

        // assert
        assertThat(isSent, equalTo(true));

    }
}
