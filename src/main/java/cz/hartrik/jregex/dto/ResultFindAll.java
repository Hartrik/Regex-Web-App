package cz.hartrik.jregex.dto;

import java.util.Collections;
import java.util.List;

/**
 *
 * @version 2017-02-15
 * @author Patrik Harag
 */
public class ResultFindAll extends Result {

    private final List<Group> groups;

    public ResultFindAll(List<Group> groups) {
        this.groups = Collections.unmodifiableList(groups);
    }

    public List<Group> getGroups() {
        return groups;
    }

}
