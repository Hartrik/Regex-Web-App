package cz.hartrik.jregex.dto;

import java.util.Collections;
import java.util.List;

/**
 *
 * @version 2017-02-13
 * @author Patrik Harag
 */
public class ResultMatch extends Result {

    private final boolean match;
    private final List<Group> groups;

    public ResultMatch(boolean match, List<Group> groups) {
        this.match = match;
        this.groups = Collections.unmodifiableList(groups);
    }

    public boolean getMatch() {
        return match;
    }

    public List<Group> getGroups() {
        return groups;
    }

}
