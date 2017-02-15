package cz.hartrik.jregex.service;

import cz.hartrik.jregex.dto.Result;
import java.util.regex.Pattern;

/**
 * Interface for regex provider (match, split...).
 *
 * @author Patrik Harag
 * @version 2017-02-13
 */
public interface RegexProvider<T extends Result> {

    T apply(Pattern p, String input);

}
