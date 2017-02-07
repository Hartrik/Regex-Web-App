package cz.hartrik.jregex.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @version 2017-02-07
 * @author Patrik Harag
 */
@Service
public class RegexEvaluator {

    private Pattern createPattern(RegexRequest request) {
        return Pattern.compile(request.getPattern());
    }

    public RegexResponse<MatchResult> match(RegexRequest request) {
        Pattern p = createPattern(request);

        List<MatchResult> results = request.getInputs().stream()
                .map(input -> doMatch(p.matcher(input)))
                .collect(Collectors.toList());

        return new RegexResponse<>(results);
    }

    private MatchResult doMatch(Matcher m) {
        boolean matches = m.matches();
        return new MatchResult(matches);
    }

}
