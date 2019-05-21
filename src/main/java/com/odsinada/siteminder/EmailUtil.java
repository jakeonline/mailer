package com.odsinada.siteminder;

import com.odsinada.siteminder.sendgrid.*;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.odsinada.siteminder.MailGunEmailProvider.*;

public class EmailUtil {

    public static List<NameValuePair> buildMailGunParams(EmailDetails email) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(FIELD_FROM, email.getFrom()));

        email.getTo().forEach(toRecipient -> params.add(new BasicNameValuePair(FIELD_TO, toRecipient)));
        email.getCc().forEach(ccRecipient -> params.add(new BasicNameValuePair(FIELD_CC, ccRecipient)));
        email.getBcc().forEach(bccRecipient -> params.add(new BasicNameValuePair(FIELD_BCC, bccRecipient)));

        params.add(new BasicNameValuePair(FIELD_SUBJECT, email.getSubject()));
        params.add(new BasicNameValuePair(FIELD_TEXT, email.getBody()));
        return params;
    }

    public static SendGridEmail buildSendGridEmail(EmailDetails email) {
        List<To> toList = new ArrayList<>();
        email.getTo().forEach(toRecipient -> toList.add(To.builder().email(toRecipient).build()));

        List<Cc> ccList = new ArrayList<>();
        email.getCc().forEach(ccRecipient -> ccList.add(Cc.builder().email(ccRecipient).build()));

        List<Bcc> bccList = new ArrayList<>();
        email.getBcc().forEach(bccRecipient -> bccList.add(Bcc.builder().email(bccRecipient).build()));

        Personalization personalization = Personalization.builder()
                .to(toList)
                .cc(ccList)
                .bcc(bccList)
                .build();

        From from = From.builder().email(email.getFrom()).build();

        Content content = Content.builder()
                .type(ContentType.TEXT_PLAIN.getMimeType())
                .value(email.getBody())
                .build();

        return SendGridEmail.builder()
                .personalizations(Arrays.asList(personalization))
                .from(from)
                .subject(email.getSubject())
                .content(Arrays.asList(content))
                .build();
    }

}
