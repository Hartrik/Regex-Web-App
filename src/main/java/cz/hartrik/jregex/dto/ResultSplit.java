package cz.hartrik.jregex.dto;

import java.util.Collections;
import java.util.List;

/**
 *
 * @version 2017-02-13
 * @author Patrik Harag
 */
public class ResultSplit {

    private final List<Group> groups;

    public ResultSplit(List<Group> groups) {
        this.groups = Collections.unmodifiableList(groups);
    }

    public List<Group> getGroups() {
        return groups;
    }

}
