package com.odsinada.siteminder;

import java.util.ArrayList;
import java.util.List;

public class OutputImpl implements Output {
    private List<String> errors = new ArrayList<>();
    private List<String> failures = new ArrayList<>();

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public List<String> getFailures() {
        return failures;
    }
}
