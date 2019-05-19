package com.odsinada.siteminder;

import lombok.Builder;
import lombok.Singular;

import javax.validation.constraints.Email;
import java.util.List;

@Builder
public class EmailDetails {

    @Email(message = "{user.email.invalid}")
    String from;

    @Singular("to")
    List<@Email(message = "{user.email.invalid}")  String> to;

    @Singular("cc")
    List<@Email(message = "{user.email.invalid}") String> cc;

    @Singular("bcc")
    List<@Email(message = "{user.email.invalid}") String> bcc;

    String body;

}
