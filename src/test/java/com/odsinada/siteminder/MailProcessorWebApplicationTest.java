package com.odsinada.siteminder;

import com.odsinada.siteminder.web.EmailDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MailProcessorWebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MailProcessorWebApplicationTest {
    private static final String API_ROOT
            = "http://localhost:8080/v1/email";

    @Test
    public void shouldBeReachable() {
        Response response = RestAssured.get(API_ROOT);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK.value()));
    }

    @Test
    public void shouldSendEmail() {
        EmailDTO emailDTO = EmailDTO.builder()
                .from("jacob@odsinada.com")
                .to(Arrays.asList("jake.odsinada@gmail.com"))
                .cc(Arrays.asList("abigail@odsinada.com"))
                .bcc(Arrays.asList("jake@odsinada.com"))
                .subject("Live Test - " + LocalDateTime.now())
                .body("Live Test - Hello World")
                .build();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(emailDTO)
                .post(API_ROOT);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED.value()));
    }

    @Test
    public void shouldRejectInvalidEmail() {
        EmailDTO emailDTO = EmailDTO.builder()
                .from("jacob@odsinada.com")
                .to(Arrays.asList("incorrectEmailFormat"))
                .subject("Live Test - " + LocalDateTime.now())
                .build();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(emailDTO)
                .post(API_ROOT);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST.value()));
    }

}
