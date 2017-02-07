package cz.hartrik.jregex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @version 2017-02-07
 * @author Patrik Harag
 */
@Controller
public class IndexController {

    @RequestMapping({"", "/index"})
    public String indexHandler() {
        return "index";
    }

}
