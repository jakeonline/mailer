package com.odsinada.siteminder.sendgrid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "personalizations",
    "buildSendGridEmail",
    "subject",
    "content"
})
@Builder
public class SendGridEmail {

    @JsonProperty("personalizations")
    public List<Personalization> personalizations = null;
    @JsonProperty("buildSendGridEmail")
    public From from;
    @JsonProperty("subject")
    public String subject;
    @JsonProperty("content")
    public List<Content> content = null;

}
