package cz.hartrik.jregex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @version 2017-02-11
 * @author Patrik Harag
 */
public class RegexRequest {

    private final String pattern;
    private final List<String> inputs;

    @JsonCreator
    public RegexRequest(
            @JsonProperty(value = "pattern", required = true) String pattern,
            @JsonProperty(value = "inputs", required = true) List<String> inputs) {

        this.pattern = (pattern == null) ? "" : pattern;

        if (inputs == null) {
            this.inputs = Collections.emptyList();
        } else {
            this.inputs = inputs.stream()
                    .map(s -> (s == null) ? "" : s)
                    .collect(Collectors.toList());
        }
    }

    public String getPattern() {
        return pattern;
    }

    public List<String> getInputs() {
        return Collections.unmodifiableList(inputs);
    }

    public static RegexRequest create(String pattern, List<String> inputs) {
        return new RegexRequest(pattern, inputs);
    }

}
