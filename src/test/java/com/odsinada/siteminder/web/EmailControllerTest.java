package com.odsinada.siteminder.web;

import com.odsinada.siteminder.service.EmailDetails;
import com.odsinada.siteminder.service.MailProcessor;
import com.odsinada.siteminder.service.Output;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailControllerTest {

    public static final String API_CONTEXT = EmailController.API_ROOT + "/";
    EmailController controller;
    String resourceUrl = "someResourceUrl";
    @Mock
    private EmailDTO emailDTO;
    @Mock
    private EmailDetails emailDetails;
    @Mock
    private Output output;
    @Mock
    private MailProcessor processor;

    @Before
    public void setup() {
        controller = new EmailController();
        ReflectionTestUtils.setField(controller, "processor", processor);
    }

    @Test
    public void shouldSendMailAndReturnCreatedStatus() throws URISyntaxException {
        // arrange
        when(processor.process(any())).thenReturn(output);
        when(output.getResource()).thenReturn(resourceUrl);

        // act
        ResponseEntity<EmailSummaryDTO> emailSummaryDTO = controller.send(emailDTO);

        // assert
        assertThat(emailSummaryDTO.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(emailSummaryDTO.getBody().getSelf(), equalTo(API_CONTEXT + resourceUrl));
        assertThat(emailSummaryDTO.getBody().getErrors(), is(empty()));

    }

    @Test
    public void shouldNotSendMailAndReturnBadRequestStatus() throws URISyntaxException {
        // arrange
        when(processor.process(any())).thenReturn(output);

        List<String> errors = Arrays.asList("Error 1");
        when(output.getErrors()).thenReturn(errors);

        // act
        ResponseEntity<EmailSummaryDTO> emailSummaryDTO = controller.send(emailDTO);

        // assert
        assertThat(emailSummaryDTO.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        assertThat(emailSummaryDTO.getBody().getSelf(), emptyString());
        assertThat(emailSummaryDTO.getBody().getErrors(), is(not(empty())));
    }

    @Test
    public void shouldNotSendMailAndReturnBadGatewayStatus() throws URISyntaxException {
        // arrange
        when(processor.process(any())).thenReturn(output);

        List<String> failures = Arrays.asList("Failure 1");
        when(output.getFailures()).thenReturn(failures);

        // act
        ResponseEntity<EmailSummaryDTO> emailSummaryDTO = controller.send(emailDTO);

        // assert
        assertThat(emailSummaryDTO.getStatusCode(), equalTo(HttpStatus.BAD_GATEWAY));
        assertThat(emailSummaryDTO.getBody().getSelf(), emptyString());
        assertThat(emailSummaryDTO.getBody().getErrors(), is(empty()));
    }

}
