package cz.hartrik.jregex.service;

import cz.hartrik.jregex.dto.Group;
import cz.hartrik.jregex.dto.ResultMatch;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Patrik Harag
 * @version 2017-02-13
 */
@Service
public class RegexMatchProvider implements RegexProvider<ResultMatch> {

    @Override
    public ResultMatch apply(Pattern p, String input) {
        Matcher m = p.matcher(input);
        boolean matches = m.matches();

        return new ResultMatch(matches,
                (matches ? collectGroups(m) : Collections.emptyList()));
    }

    private List<Group> collectGroups(Matcher m) {
        List<Group> groups = new ArrayList<>(m.groupCount());
        for (int i = 1; i <= m.groupCount(); i++) {
            // group #0 intentionally ignored
            Group g = new Group(m.start(i), m.end(i));
            groups.add(g);
        }
        return groups;
    }

}
