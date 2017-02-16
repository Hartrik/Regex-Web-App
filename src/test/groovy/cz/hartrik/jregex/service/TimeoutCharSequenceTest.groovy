package cz.hartrik.jregex.service

import org.junit.Test
import java.util.regex.Pattern

/**
 *
 * @version 2017-02-16
 * @author Patrik Harag
 */
class TimeoutCharSequenceTest {

    @Test
    void testWorksProperly() {
        def p = Pattern.compile('abc')

        assert   p.matcher(new TimeoutCharSequence("abc", 1000)).matches()
        assert ! p.matcher(new TimeoutCharSequence("abcd", 1000)).matches()
    }

    // ---

    Pattern dangerousPattern = Pattern.compile("(aa|aab?)*");
    String dangerousInput = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    //@Test // for comparsion
    void testOldWay() {
        dangerousPattern.matcher(dangerousInput).matches()
    }

    @Test(expected = RuntimeException)
    void testStop200() {
        def safe = new TimeoutCharSequence(dangerousInput, 200)
        dangerousPattern.matcher(safe).matches()
    }

    @Test(expected = RuntimeException)
    void testStop100() {
        def safe = new TimeoutCharSequence(dangerousInput, 100)
        dangerousPattern.matcher(safe).matches()
    }

}
