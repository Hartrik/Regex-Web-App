package cz.hartrik.jregex.service;

import cz.hartrik.jregex.dto.Group;
import cz.hartrik.jregex.dto.ResultSplit;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Patrik Harag
 * @version 2017-02-13
 */
@Service
public class RegexSplitProvider implements RegexProvider<ResultSplit> {

    @Override
    public ResultSplit apply(Pattern p, String input) {
        // We can't use Pattern#split because we need indexes

        List<Group> groups = split(p, input);
        return new ResultSplit(groups);
    }

    private List<Group> split(Pattern p, String input) {
        Matcher m = p.matcher(input);

        ArrayList<String> matchList = new ArrayList<>();
        ArrayList<Group> groupList = new ArrayList<>();

        int index = 0;

        // Add segments before each match found
        while(m.find()) {
            if (index == 0 && (index == m.start()) && (m.start() == m.end())) {
                // no empty leading substring included for zero-width match
                // at the beginning of the input char sequence.
                continue;
            }
            String match = input.subSequence(index, m.start()).toString();
            matchList.add(match);
            groupList.add(Group.of(index, m.start()));
            index = m.end();
        }

        if (index == 0) {
            // If no match was found, return this
            groupList.add(Group.of(0, input.length()));
            return groupList;
        }

        // Add remaining segment
        matchList.add(input.subSequence(index, input.length()).toString());
        groupList.add(Group.of(index, input.length()));

        // Construct result
        int resultSize = matchList.size();

        while (resultSize > 0 && matchList.get(resultSize-1).equals(""))
            resultSize--;

        return groupList.subList(0, resultSize);
    }

}
