package cz.hartrik.jregex.config;

import cz.hartrik.jregex.service.TimeoutCharSequence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * @author Patrik Harag
 * @version 2017-02-21
 */
@Configuration
public class RegexConfig {

    /**
     * Max number of inputs to process for one request.
     */
    public static final int MAX_INPUTS = 10;

    /**
     * Timeout for one input.
     */
    private static final int TIMEOUT_MILLIS = 1_000;

    @Bean
    public BiFunction<Pattern, String, Matcher> matcherProvider() {
        return (pattern, s) -> pattern.matcher(new TimeoutCharSequence(s, TIMEOUT_MILLIS));
    }

}
