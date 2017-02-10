package cz.hartrik.jregex.service;

import java.util.Collections;
import java.util.List;

/**
 *
 * @version 2017-02-10
 * @author Patrik Harag
 */
public class MatchResult {

    private final boolean match;
    private final List<Group> groups;

    public MatchResult(boolean match, List<Group> groups) {
        this.match = match;
        this.groups = Collections.unmodifiableList(groups);
    }

    public boolean getMatch() {
        return match;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public static class Group {

        private final int start;
        private final int end;

        public Group(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getEnd() {
            return end;
        }

        public int getStart() {
            return start;
        }
    }

}
