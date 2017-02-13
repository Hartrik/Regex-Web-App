package cz.hartrik.jregex

import cz.hartrik.jregex.service.RegexSplitProvider
import org.junit.Test
import java.util.regex.Pattern

/**
 *
 * @version 2017-02-13
 * @author Patrik Harag
 */
class RegexSplitProviderTest {

    def provider = new RegexSplitProvider()

    void compareWithPatternSplit(String regex, String input) {
        def p = Pattern.compile(regex)

        def strings = p.split(input)                  // default split
        def groups = provider.apply(p, input).groups  // my split

        assert strings.size() == groups.size()

        [strings, groups].transpose().each {  // zip
            def (string, group) = it
            assert string == input.substring(group.start, group.end)
        }
    }


    @Test
    void simpleSplit() {
        compareWithPatternSplit "_", "aa_bb"
    }

    @Test
    void commaSplit() {
        compareWithPatternSplit ",", "aa,bb,cc"
        compareWithPatternSplit ",", ",bb,cc"
        compareWithPatternSplit ",", ",,bb,cc"
        compareWithPatternSplit ",", "aa,bb,"
        compareWithPatternSplit ",", "aa,bb,,"
        compareWithPatternSplit ",", "aa,,cc"
    }

    @Test
    void patternIsNotInInput() {
        compareWithPatternSplit "pattern", "abc"
    }

}
