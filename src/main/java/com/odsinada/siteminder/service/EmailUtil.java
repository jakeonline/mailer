package com.odsinada.siteminder.service;

import com.odsinada.siteminder.sendgrid.*;
import com.odsinada.siteminder.web.EmailDTO;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EmailUtil {

    public static List<NameValuePair> buildMailGunParams(EmailDetails email) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(MailGunEmailProvider.FIELD_FROM, email.getFrom()));

        email.getTo().forEach(toRecipient -> params.add(new BasicNameValuePair(MailGunEmailProvider.FIELD_TO, toRecipient)));
        email.getCc().forEach(ccRecipient -> params.add(new BasicNameValuePair(MailGunEmailProvider.FIELD_CC, ccRecipient)));
        email.getBcc().forEach(bccRecipient -> params.add(new BasicNameValuePair(MailGunEmailProvider.FIELD_BCC, bccRecipient)));

        params.add(new BasicNameValuePair(MailGunEmailProvider.FIELD_SUBJECT, email.getSubject()));
        params.add(new BasicNameValuePair(MailGunEmailProvider.FIELD_TEXT, email.getBody()));
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

    public static EmailDetails toEntity(EmailDTO emailDTO) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setId(createUniqueResource());
        emailDetails.setFrom(emailDTO.getFrom());
        emailDetails.setTo(emailDTO.getTo());
        emailDetails.setCc(emailDTO.getCc());
        emailDetails.setBcc(emailDTO.getBcc());
        emailDetails.setSubject(emailDTO.getSubject());
        emailDetails.setBody(emailDTO.getBody());
        return emailDetails;
    }

    private static Long createUniqueResource() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);

        return Long.valueOf(dateTime.format(formatter) + randomNum);
    }
}
