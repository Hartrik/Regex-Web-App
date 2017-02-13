package cz.hartrik.jregex.controller

import cz.hartrik.jregex.config.MVCConfig
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestContextManager
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 *
 * @version 2017-02-12
 * @author Patrik Harag
 */
@WebAppConfiguration
@ContextConfiguration(classes = [ MVCConfig.class ])
abstract class HelperAbstractMvcTest {

    // I always try not to use inheritance in unit testing.
    // This is an exception...

    @Autowired
    private WebApplicationContext context

    @Before
    void setup() {
        setupSpring()
        setupMVC()
    }

    // --- alternative for @RunWith(SpringJUnit4ClassRunner.class)

    private TestContextManager testContextManager;

    void setupSpring() {
        this.testContextManager = new TestContextManager(getClass())
        this.testContextManager.prepareTestInstance(this)
    }

    // ---

    MockMvc mockMvc

    void setupMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

}
