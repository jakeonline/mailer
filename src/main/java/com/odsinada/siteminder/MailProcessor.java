package com.odsinada.siteminder;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class MailProcessor {
    public static final String MSG_FAILED_SENDING_EMAIL = "Failed sending email.";
    private final MailDeliveryService mailDeliveryService;
    private final Validator validator;

    public MailProcessor(MailDeliveryService mailDeliveryService, Validator validator) {
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
            } catch (MailDeliveryException mde) {
                output.getFailures().add(MSG_FAILED_SENDING_EMAIL);
            }
        }

        return output;
    }

}
