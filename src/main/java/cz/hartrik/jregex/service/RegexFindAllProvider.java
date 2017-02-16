package cz.hartrik.jregex.service;

import cz.hartrik.jregex.dto.Group;
import cz.hartrik.jregex.dto.ResultFindAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Patrik Harag
 * @version 2017-02-16
 */
@Service
public class RegexFindAllProvider extends AbstractRegexProvider<ResultFindAll> {

    public RegexFindAllProvider() {
        super();
    }

    @Autowired
    public RegexFindAllProvider(BiFunction<Pattern, String, Matcher> matcherProvider) {
        super(matcherProvider);
    }

    @Override
    public ResultFindAll apply(Pattern p, String input) {
        List<Group> groups = findAll(p, input);
        return new ResultFindAll(groups);
    }

    private List<Group> findAll(Pattern p, String input) {
        Matcher m = createMatcher(p, input);
        List<Group> groups = new ArrayList<>();

        while (m.find()) {
            groups.add(Group.of(m.start(), m.end()));
        }

        return groups;
    }

}
