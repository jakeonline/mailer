package com.odsinada.siteminder;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MailGunEmailProvider implements EmailProvider {

    public static final String USER_NAME = "api";
    public static final String API_KEY = "ac292edb8ec707f456795207c6ad417d-4a62b8e8-f37f737b";
    public static final String FIELD_FROM = "from";
    public static final String FIELD_TO = "to";
    public static final String FIELD_CC = "cc";
    public static final String FIELD_BCC = "bcc";
    public static final String FIELD_SUBJECT = "subject";
    public static final String FIELD_TEXT = "text";
    private static final String MAILGUN_URL = "https://api.mailgun.net/v3/sandbox16ae66a08f7b486eb121269181144231.mailgun.org/messages";

    @Override
    public boolean send(EmailDetails email) {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(MAILGUN_URL);

        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials(USER_NAME, API_KEY);
        try {
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
        } catch (AuthenticationException aex) {
            log.error("Error adding authentication header.", aex);
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(FIELD_FROM, email.getFrom()));

        email.getTo().forEach(to_recipient -> params.add(new BasicNameValuePair(FIELD_TO, to_recipient)));
        email.getCc().forEach(cc_recipient -> params.add(new BasicNameValuePair(FIELD_CC, cc_recipient)));
        email.getBcc().forEach(bcc_recipient -> params.add(new BasicNameValuePair(FIELD_BCC, bcc_recipient)));

        params.add(new BasicNameValuePair(FIELD_SUBJECT, email.getSubject()));
        params.add(new BasicNameValuePair(FIELD_TEXT, email.getBody()));

        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        try {
            CloseableHttpResponse response = client.execute(httpPost);
            log.info("Status: " + response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return true;
            }
        } catch (IOException iox) {
            log.error("Error calling Mailgun API.", iox);
        }

        return false;
    }
}
