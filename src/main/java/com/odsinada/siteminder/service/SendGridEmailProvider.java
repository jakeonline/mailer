package com.odsinada.siteminder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SendGridEmailProvider implements EmailProvider {

    private static final String API_KEY = "";
    private static final String SENDGRID_URL = "https://api.sendgrid.com/v3/mail/send";

    private ObjectMapper mapper;

    public SendGridEmailProvider(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean send(EmailDetails email) {

        String json = null;
        try {
            json = mapper.writeValueAsString(EmailUtil.buildSendGridEmail(email));
        } catch (JsonProcessingException jpex) {
            log.error("Error converting email details to JSON string", jpex);
            return false;
        }

        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            response = client.execute(createHttpPost(json));
        } catch (IOException iox) {
            log.error("Error calling SendGrip API.", iox);
            return false;
        }

        boolean isSuccess = response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED;

        log.info("Status: {}. SendGrip API send status: {}", response.getStatusLine(), isSuccess);

        return isSuccess;
    }

    private HttpPost createHttpPost(String json) {
        HttpPost httpPost = new HttpPost(SENDGRID_URL);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        return httpPost;
    }
}
