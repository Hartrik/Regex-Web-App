package cz.hartrik.jregex.controller

import cz.hartrik.jregex.config.MVCConfig
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 *
 * @version 2017-02-12
 * @author Patrik Harag
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = [ MVCConfig.class ])
abstract class HelperAbstractMvcTest {

    @Autowired
    private WebApplicationContext context

    MockMvc mockMvc

    @Before
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    // I always try not to use inheritance in unit testing.
    // But I don't know what to do with all of those annotations...

}
