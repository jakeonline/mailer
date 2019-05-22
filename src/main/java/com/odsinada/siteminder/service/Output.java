package com.odsinada.siteminder.service;

import java.util.List;

public interface Output {
    String getResource();
    void setResource(String resource);

    List<String> getErrors();

    List<String> getFailures();
}
