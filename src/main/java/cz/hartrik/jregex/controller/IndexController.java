package cz.hartrik.jregex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @version 2017-02-07
 * @author Patrik Harag
 */
@Controller
public class IndexController {

    @RequestMapping({"", "/index"})
    public String indexHandler(Model model) {
        model.addAttribute("message", "Hello");

        return "index";
    }

    @RequestMapping("*")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String fallbackHandler() {
        return "404";
    }

}
