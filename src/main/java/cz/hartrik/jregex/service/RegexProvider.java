package cz.hartrik.jregex.service;

import java.util.regex.Pattern;

/**
 * Interface for regex provider (match, split...).
 *
 * @author Patrik Harag
 * @version 2017-02-13
 */
public interface RegexProvider<T> {

    T apply(Pattern p, String input);

}
