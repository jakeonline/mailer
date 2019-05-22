package com.odsinada.siteminder.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "errors"
})
@Builder
@Getter
public class EmailSummaryDTO {

    @JsonProperty("self")
    public String self;
    @JsonProperty("errors")
    public List<String> errors = null;
}