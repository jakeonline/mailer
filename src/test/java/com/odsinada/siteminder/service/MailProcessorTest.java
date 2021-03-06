package com.odsinada.siteminder.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MailProcessorTest {
    @Mock
    EmailDetails input;
    private MailProcessor processor;
    @Mock
    private Output output;
    @Mock
    private MailDeliveryService mailDeliveryService;
    @Mock
    private Validator validator;

    private java.util.Set<javax.validation.ConstraintViolation<EmailDetails>> violations = new HashSet<>();
    @Mock
    private ConstraintViolation<EmailDetails> violationA;

    @Before
    public void setup() {
        processor = new MailProcessor(mailDeliveryService, validator);
    }

    @Test
    public void shouldProcessInput() throws Exception {
        // arrange
        when(validator.validate(input)).thenReturn(violations);

        // act
        Output output = processor.process(input);

        // assert
        assertThat(output.getResource(), notNullValue());
        assertThat(output.getErrors().isEmpty(), equalTo(true));
        verify(mailDeliveryService).send(input);
    }

    @Test
    public void shouldInvalidateAnyBadInput() throws Exception {
        // arrange
        violations.add(violationA);
        when(violationA.getMessage()).thenReturn("has violationA");
        when(validator.validate(input)).thenReturn(violations);

        // act
        Output output = processor.process(input);

        // assert
        assertThat(output.getResource(), nullValue());
        assertThat(output.getErrors().isEmpty(), equalTo(false));
        verify(mailDeliveryService, times(0)).send(input);
    }

    @Test
    public void shouldReportFailures() throws Exception {
        // arrange
        when(validator.validate(input)).thenReturn(violations);
        doThrow(new MailDeliveryException()).when(mailDeliveryService).send(input);

        // act
        Output output = processor.process(input);

        // assert
        assertThat(output.getResource(), notNullValue());
        assertThat(output.getErrors().isEmpty(), equalTo(true));
        assertThat(output.getFailures().isEmpty(), equalTo(false));
    }


}
