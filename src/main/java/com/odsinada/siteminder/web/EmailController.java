package com.odsinada.siteminder.web;

import com.odsinada.siteminder.service.EmailDetails;
import com.odsinada.siteminder.service.EmailUtil;
import com.odsinada.siteminder.service.MailProcessor;
import com.odsinada.siteminder.service.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
@RequestMapping("/v1/email")
public class EmailController {

    @Autowired
    private MailProcessor processor;

    @GetMapping
    public ResponseEntity<EmailDTO> getFormat() {
        EmailDTO emailDTO = EmailDTO.builder()
                .from("admin@info.com")
                .to(Arrays.asList("john@doe.com", "jane@doe.com"))
                .cc(Arrays.asList("peter@smith.com", "lisa@smith.com"))
                .bcc(Arrays.asList("james@jones.com", "holly@jones.com"))
                .build();

        return ResponseEntity.ok(emailDTO);
    }

    @PostMapping
    public ResponseEntity<EmailSummaryDTO> send(@RequestBody EmailDTO emailDTO) throws URISyntaxException {
        EmailDetails emailDetails = EmailUtil.toEntity(emailDTO);

        Output output = processor.process(emailDetails);

        EmailSummaryDTO emailSummaryDTO = EmailSummaryDTO.builder()
                .self(output.getResource())
                .errors(output.getErrors())
                .build();

        if (!output.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(emailSummaryDTO);
        }

        if (!output.getFailures().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(emailSummaryDTO);
        }

        return ResponseEntity.created(new URI(output.getResource())).body(emailSummaryDTO);
    }

}