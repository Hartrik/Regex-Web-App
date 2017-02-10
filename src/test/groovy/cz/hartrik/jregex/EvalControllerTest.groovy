package cz.hartrik.jregex

import com.fasterxml.jackson.databind.ObjectMapper
import cz.hartrik.jregex.config.MVCConfig
import cz.hartrik.jregex.service.RegexRequest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = [ MVCConfig.class ])
class EvalControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    static final def JSON_UTF8 = MediaType.parseMediaType("application/json;charset=UTF-8");

    static def toJsonBytes(object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }

    // --------------------

    @Test
    void testExampleRequest() {
        mockMvc.perform(get("/eval/example-request").accept(JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("\$.pattern").exists())
            .andExpect(jsonPath("\$.inputs").exists())
    }

    @Test
    void testMatch() {
        mockMvc.perform(post("/eval/match")
                .contentType(JSON_UTF8)
                .content(toJsonBytes(RegexRequest.create("a+", ["b", "a", "aa"])))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("\$.exception").doesNotExist())
            .andExpect(jsonPath("\$.results").exists())
            .andExpect(jsonPath("\$.results[0].match").value(false))
            .andExpect(jsonPath("\$.results[1].match").value(true))
            .andExpect(jsonPath("\$.results[2].match").value(true))
            .andExpect(jsonPath("\$.results[3]").doesNotExist())
    }

}