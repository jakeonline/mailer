package com.odsinada.siteminder.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

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
            violations.forEach((viol) -> output.getErrors().add(viol.getMessage() + "|" + viol.getPropertyPath()));
        } else {
            try {
                mailDeliveryService.send(input);
            } catch (MailDeliveryException mde) {
                output.getFailures().add(MSG_FAILED_SENDING_EMAIL);
            }
            output.setResource(input.getId().toString());
        }

        return output;
    }

}
