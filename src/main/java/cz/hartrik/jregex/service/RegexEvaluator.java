package cz.hartrik.jregex.service;

import cz.hartrik.jregex.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @version 2017-02-15
 * @author Patrik Harag
 */
@Service
public class RegexEvaluator {

    @Autowired private RegexMatchProvider matchProvider;
    @Autowired private RegexFindAllProvider findAllProvider;
    @Autowired private RegexSplitProvider splitProvider;

    public RegexResponse<ResultMatch> match(RegexRequest request) {
        return apply(request, matchProvider);
    }

    public RegexResponse<ResultFindAll> findAll(RegexRequest request) {
        return apply(request, findAllProvider);
    }

    public RegexResponse<ResultSplit> split(RegexRequest request) {
        return apply(request, splitProvider);
    }

    // universal

    private <T extends Result> RegexResponse<T> apply(
            RegexRequest request, RegexProvider<T> provider) {

        try {
            Pattern p = createPattern(request);
            List<T> results = request.getInputs().stream()
                    .map(input -> provider.apply(p, input))
                    .collect(Collectors.toList());

            return new RegexResponse<>(results);

        } catch (Exception ex) {
            return new RegexResponse<>(ex);
        }
    }

    private Pattern createPattern(RegexRequest request) {
        return Pattern.compile(request.getPattern());
    }

}
