package com.odsinada.siteminder.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShuffledProviderDeliveryServiceTest {

    private ShuffledProviderDeliveryService service;
    private EmailDetails email;

    @Mock
    private EmailProvider providerA, providerB, providerC;

    @Before
    public void setup() {
        List<EmailProvider> providers = Arrays.asList(providerA, providerB, providerC);
        service = new ShuffledProviderDeliveryService(providers){
            @Override
            protected void shuffle(List<EmailProvider> emailProviders) {
                // bypass shuffling for unit testing
            }

        };
    }

    @Test
    public void shouldSendMailOnInitialProvider() throws MailDeliveryException {
        // arrange
        when(providerA.send(email)).thenReturn(true);

        // act
        service.send(email);

        // assert
        verify(providerA, times(1)).send(email);
        verify(providerB, times(0)).send(email);
        verify(providerC, times(0)).send(email);
    }

    @Test
    public void shouldSendMailOnNonInitialProvider() throws MailDeliveryException {
        // arrange
        when(providerA.send(email)).thenReturn(false);
        when(providerB.send(email)).thenReturn(false);
        when(providerC.send(email)).thenReturn(true);

        // act
        service.send(email);

        // assert
        verify(providerA, times(1)).send(email);
        verify(providerB, times(1)).send(email);
        verify(providerC, times(1)).send(email);
    }

    @Test
    public void shouldFailSendMailOnUnsuccesfulProviders() throws MailDeliveryException {
        // arrange
        when(providerA.send(email)).thenReturn(false);
        when(providerB.send(email)).thenReturn(false);
        when(providerC.send(email)).thenReturn(false);

        // act
        try {
            service.send(email);
            fail("Incorrect scenario - mail should not be successfully sent");
        } catch (MailDeliveryException mde) {
            // assert
            verify(providerA, times(1)).send(email);
            verify(providerB, times(1)).send(email);
            verify(providerC, times(1)).send(email);
        }

    }
}
