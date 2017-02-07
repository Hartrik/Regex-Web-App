package cz.hartrik.jregex.service;

import java.util.List;

/**
 *
 * @version 2017-02-07
 * @author Patrik Harag
 */
public class RegexResponse <T extends Result> {

    private final List<T> results;

    public RegexResponse(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }

}
