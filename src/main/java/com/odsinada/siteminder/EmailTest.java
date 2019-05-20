package com.odsinada.siteminder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class EmailTest {

    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        EmailDetails emailDetails = EmailDetails.builder()
                .from("jacobodsinada")
                .to("jodsi")
                .to("Hello <j@odsinad.com>")
                .cc("jallll")
                .bcc("jallll")
                .build();

        Set<ConstraintViolation<EmailDetails>> violations = validator.validate(emailDetails);
        violations.forEach((viol) -> System.out.println(viol.getMessage()));
    }
}
