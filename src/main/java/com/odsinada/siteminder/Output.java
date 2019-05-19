package com.odsinada.siteminder;

import java.util.List;

public interface Output {
    List<String> getErrors();

    List<String> getFailures();
}
