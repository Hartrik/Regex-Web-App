package cz.hartrik.jregex.controller;

import cz.hartrik.jregex.service.RegexEvaluator;
import cz.hartrik.jregex.dto.RegexRequest;
import cz.hartrik.jregex.dto.RegexResponse;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @version 2017-02-15
 * @author Patrik Harag
 */
@RestController
@RequestMapping("/eval")
public class EvalController {

    @Autowired
    private RegexEvaluator evaluator;

    @RequestMapping("example-request")
    public RegexRequest exampleHandler() {
        return new RegexRequest("aa*b", Arrays.asList("ab", "test", "aaab"));
    }

    @RequestMapping(value = "match", method = RequestMethod.POST)
    public RegexResponse<?> matchHandler(@RequestBody RegexRequest request) {
        return evaluator.match(request);
    }

    @RequestMapping(value = "find-all", method = RequestMethod.POST)
    public RegexResponse<?> findAllHandler(@RequestBody RegexRequest request) {
        return evaluator.findAll(request);
    }

    @RequestMapping(value = "split", method = RequestMethod.POST)
    public RegexResponse<?> splitHandler(@RequestBody RegexRequest request) {
        return evaluator.split(request);
    }

}
