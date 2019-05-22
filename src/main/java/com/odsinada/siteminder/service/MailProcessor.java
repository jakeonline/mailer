package com.odsinada.siteminder.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MailProcessor {
    public static final String MSG_FAILED_SENDING_EMAIL = "Failed sending email.";
    private final MailDeliveryService mailDeliveryService;
    private final Validator validator;

    public MailProcessor(@Autowired MailDeliveryService mailDeliveryService, @Autowired Validator validator) {
        this.mailDeliveryService = mailDeliveryService;
        this.validator = validator;
    }

    public Output process(EmailDetails input) {
        Output output = new OutputImpl();

        Set<ConstraintViolation<EmailDetails>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            violations.forEach((viol) -> output.getErrors().add(viol.getMessage()));
        } else {
            try {
                mailDeliveryService.send(input);
                output.setResource(createUniqueResource());
            } catch (MailDeliveryException mde) {
                output.getFailures().add(MSG_FAILED_SENDING_EMAIL);
            }
        }

        return output;
    }

    private String createUniqueResource() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);

        return dateTime.format(formatter) + randomNum;
    }

}
