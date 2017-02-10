package cz.hartrik.jregex.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @version 2017-02-07
 * @author Patrik Harag
 */
public class RegexRequest {

    private final String pattern;
    private final List<String> inputs;

    @JsonCreator
    public RegexRequest(
            @JsonProperty(value = "pattern", required = true) String pattern,
            @JsonProperty(value = "inputs", required = true) List<String> inputs) {

        this.pattern = pattern;
        this.inputs = inputs;
    }

    public String getPattern() {
        return pattern;
    }

    public List<String> getInputs() {
        return inputs;
    }

    public static RegexRequest create(String pattern, List<String> inputs) {
        return new RegexRequest(pattern, inputs);
    }

}
