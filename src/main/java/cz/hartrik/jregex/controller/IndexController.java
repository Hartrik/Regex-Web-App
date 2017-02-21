package cz.hartrik.jregex.controller;

import cz.hartrik.jregex.config.RegexConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @version 2017-02-21
 * @author Patrik Harag
 */
@Controller
public class IndexController {

    @RequestMapping({"", "/index"})
    public String indexHandler(Model model) {
        model.addAttribute("MAX_INPUTS", RegexConfig.MAX_INPUTS);

        return "index";
    }

}
