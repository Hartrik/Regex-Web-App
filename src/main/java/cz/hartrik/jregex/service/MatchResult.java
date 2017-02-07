package cz.hartrik.jregex.service;

/**
 *
 * @author Patrik Harag
 */
public class MatchResult extends Result {
    private final boolean match;

    public MatchResult(boolean match) {
        super();
        this.match = match;
    }

    public boolean getMatch() {
        return match;
    }

}
