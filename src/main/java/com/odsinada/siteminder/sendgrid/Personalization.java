package com.odsinada.siteminder.sendgrid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "to",
    "cc",
    "bcc"
})
@Builder
public class Personalization {

    @JsonProperty("to")
    public List<To> to = null;
    @JsonProperty("cc")
    public List<Cc> cc = null;
    @JsonProperty("bcc")
    public List<Bcc> bcc = null;

}
