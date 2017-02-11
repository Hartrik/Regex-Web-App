package cz.hartrik.jregex.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @version 2017-02-10
 * @author Patrik Harag
 */
@Service
public class RegexEvaluator {

    private Pattern createPattern(RegexRequest request) {
        return Pattern.compile(request.getPattern());
    }

    public RegexResponse<MatchResult> match(RegexRequest request) {
        Pattern p;
        try {
            p = createPattern(request);
        } catch (Exception ex) {
            return new RegexResponse<>(ex);
        }

        List<MatchResult> results = request.getInputs().stream()
                .map(input -> doMatch(p, input))
                .collect(Collectors.toList());

        return new RegexResponse<>(results);
    }

    private MatchResult doMatch(Pattern p, String input) {
        Matcher m = p.matcher(input);
        boolean matches = m.matches();

        return new MatchResult(matches,
                (matches ? collectGroups(m) : Collections.emptyList()));
    }

    private List<MatchResult.Group> collectGroups(Matcher m) {
        List<MatchResult.Group> groups = new ArrayList<>(m.groupCount());
        for (int i = 1; i <= m.groupCount(); i++) {
            // group #0 intentionally ignored
            MatchResult.Group g = new MatchResult.Group(m.start(i), m.end(i));
            groups.add(g);
        }
        return groups;
    }

}
