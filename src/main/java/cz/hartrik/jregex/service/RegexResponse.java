package cz.hartrik.jregex.service;

import java.util.List;

/**
 *
 * @version 2017-02-08
 * @author Patrik Harag
 */
public class RegexResponse <T extends Result> {

    private final String exception;
    private final List<T> results;

    public RegexResponse(List<T> results) {
        this.exception = null;
        this.results = results;
    }

    public RegexResponse(Exception ex) {
        this.exception = ex.getClass().getName() + ": " + ex.getMessage();
        this.results = null;
    }

    public String getException() {
        return exception;
    }

    public List<T> getResults() {
        return results;
    }

}
