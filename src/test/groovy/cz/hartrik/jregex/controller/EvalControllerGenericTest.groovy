package cz.hartrik.jregex.controller

import cz.hartrik.jregex.dto.RegexRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Tests basic things for every eval method.
 *
 * @version 2017-02-15
 * @author Patrik Harag
 */
@RunWith(Parameterized.class)
class EvalControllerGenericTest extends HelperAbstractMvcTest {

    String evalMethodUrl

    EvalControllerGenericTest(url) {
        this.evalMethodUrl = url
    }

    @Parameterized.Parameters static Collection<Object[]> data() {
        def data = [
                "/eval/match",
                "/eval/find-all",
                "/eval/split",
        ]
        return data.collect { [it] as Object[] }
    }

    ResultActions post(url, obj) {
        Helper.post(mockMvc, url, obj)
    }

    ResultActions get(url) {
        Helper.get(mockMvc, url)
    }


    @Test
    void matchPatternError() {
        post(evalMethodUrl, RegexRequest.create("a(a", ["aa"]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.exception").exists())
    }

    @Test
    void matchNulls() {
        // should not throw server error (500)
        // right now null is treated as ""

        post(evalMethodUrl, RegexRequest.create(null, ["aa"]))
                .andExpect(status().isOk())

        post(evalMethodUrl, RegexRequest.create(".*", null))
                .andExpect(status().isOk())

        post(evalMethodUrl, RegexRequest.create(".*", [null, null]))
                .andExpect(status().isOk())
    }

}
