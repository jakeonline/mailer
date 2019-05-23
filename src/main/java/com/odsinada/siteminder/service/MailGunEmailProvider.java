package com.odsinada.siteminder.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MailGunEmailProvider implements EmailProvider {

    private static final String USER_NAME = "api";
    private static final String API_KEY = "";
    public static final String FIELD_FROM = "from";
    public static final String FIELD_TO = "to";
    public static final String FIELD_CC = "cc";
    public static final String FIELD_BCC = "bcc";
    public static final String FIELD_SUBJECT = "subject";
    public static final String FIELD_TEXT = "text";
    private static final String MAILGUN_URL = "https://api.mailgun.net/v3/sandbox16ae66a08f7b486eb121269181144231.mailgun.org/errors";

    @Override
    public boolean send(EmailDetails email) {

        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            response = client.execute(createHttpPost(email));
        } catch (IOException iox) {
            log.error("Error calling Mailgun API.", iox);
            return false;
        }

        boolean isSuccess = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;

        log.info("Resource: {} | Status: {}. Mailgun API send status: {}", email.getId(), response.getStatusLine(), isSuccess);

        return isSuccess;
    }

    private HttpPost createHttpPost(EmailDetails email) {
        HttpPost httpPost = new HttpPost(MAILGUN_URL);
        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials(USER_NAME, API_KEY);
        try {
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
        } catch (AuthenticationException aex) {
            log.warn("Error adding authentication header.", aex);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(EmailUtil.buildMailGunParams(email), Consts.UTF_8));
        return httpPost;
    }

}
