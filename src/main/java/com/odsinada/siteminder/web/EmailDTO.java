
package com.odsinada.siteminder.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to",
    "cc",
    "bcc",
    "subject",
    "body"
})
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    @JsonProperty("from")
    public String from;
    @JsonProperty("to")
    public List<String> to = null;
    @JsonProperty("cc")
    public List<String> cc = null;
    @JsonProperty("bcc")
    public List<String> bcc = null;
    @JsonProperty("body")
    public String body;
    @JsonProperty("subject")
    public String subject;

}
