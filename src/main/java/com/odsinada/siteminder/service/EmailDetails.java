package com.odsinada.siteminder.service;

import lombok.*;

import javax.validation.constraints.Email;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

    public static final String MSG_EMAIL_INVALID = "Email ${validatedValue} is invalid";

    Long id;

    @Email(message = MSG_EMAIL_INVALID)
    String from;

    @Singular("to")
    List<@Email(message = MSG_EMAIL_INVALID)  String> to;

    @Singular("cc")
    List<@Email(message = MSG_EMAIL_INVALID) String> cc;

    @Singular("bcc")
    List<@Email(message = MSG_EMAIL_INVALID) String> bcc;

    String body;

    String subject;

}
