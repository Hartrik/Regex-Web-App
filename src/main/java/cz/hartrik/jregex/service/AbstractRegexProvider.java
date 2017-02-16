package cz.hartrik.jregex.service;

import cz.hartrik.jregex.dto.Result;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Patrik Harag
 * @version 2017-02-16
 */
abstract class AbstractRegexProvider<T extends Result> implements RegexProvider<T>  {

    private final BiFunction<Pattern, String, Matcher> matcherProvider;

    public AbstractRegexProvider() {
        this.matcherProvider = Pattern::matcher;
    }

    public AbstractRegexProvider(BiFunction<Pattern, String, Matcher> matcherProvider) {
        this.matcherProvider = matcherProvider;
    }

    protected final Matcher createMatcher(Pattern p, String s) {
        return matcherProvider.apply(p, s);
    }

}
