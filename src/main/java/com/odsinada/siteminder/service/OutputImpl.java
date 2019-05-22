package com.odsinada.siteminder.service;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OutputImpl implements Output {
    private List<String> errors = new ArrayList<>();
    private List<String> failures = new ArrayList<>();
    private String resource;
}
