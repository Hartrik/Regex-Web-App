package cz.hartrik.jregex.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Patrik Harag
 * @version 2017-02-16
 */
@Configuration
@ComponentScan(basePackages = {
        "cz.hartrik.jregex.config",
        "cz.hartrik.jregex.controller",
        "cz.hartrik.jregex.service",
})
public class BaseConfig {

}
