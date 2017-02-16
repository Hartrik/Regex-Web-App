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
 * @version 2017-02-16
 */
@Configuration
public class RegexConfig {

    private static int TIMEOUT_MILLIS = 2_000;

    @Bean
    public BiFunction<Pattern, String, Matcher> matcherProvider() {
        return (pattern, s) -> pattern.matcher(new TimeoutCharSequence(s, TIMEOUT_MILLIS));
    }

}
