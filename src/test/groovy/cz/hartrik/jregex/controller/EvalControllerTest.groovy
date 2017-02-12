package cz.hartrik.jregex.controller

import cz.hartrik.jregex.service.RegexRequest
import org.junit.Test
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 *
 * @version 2017-02-12
 * @author Patrik Harag
 */
class EvalControllerTest extends HelperAbstractMvcTest {

    ResultActions post(url, obj) {
        Helper.post(mockMvc, url, obj)
    }

    ResultActions get(url) {
        Helper.get(mockMvc, url)
    }

    @Test
    void exampleRequest() {
        get("/eval/example-request")
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.pattern").exists())
                .andExpect(jsonPath("\$.inputs").exists())
    }

    @Test
    void matchSimple() {
        post("/eval/match", RegexRequest.create("a+", ["b", "a", "aa"]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.exception").doesNotExist())
                .andExpect(jsonPath("\$.results").exists())
                .andExpect(jsonPath("\$.results[0].match").value(false))
                .andExpect(jsonPath("\$.results[1].match").value(true))
                .andExpect(jsonPath("\$.results[2].match").value(true))
                .andExpect(jsonPath("\$.results[3]").doesNotExist())
    }

    @Test
    void matchPatternError() {
        post("/eval/match", RegexRequest.create("a(a", ["aa"]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$.exception").exists())
    }

    @Test
    void matchNulls() {
        // should not throw server error (500)
        // right now null is treated as ""

        post("/eval/match", RegexRequest.create(null, ["aa"]))
                .andExpect(status().isOk())

        post("/eval/match", RegexRequest.create(".*", null))
                .andExpect(status().isOk())

        post("/eval/match", RegexRequest.create(".*", [null, null]))
                .andExpect(status().isOk())
    }

    @Test
    void matchGroups() {
        post("/eval/match", RegexRequest.create("a(b*)a(c*)a", ["abbacca"]))
                .andExpect(jsonPath("\$.results[0].groups[0].start").value(1))
                .andExpect(jsonPath("\$.results[0].groups[0].end").value(3))
                .andExpect(jsonPath("\$.results[0].groups[1].start").value(4))
                .andExpect(jsonPath("\$.results[0].groups[1].end").value(6))
    }

}
