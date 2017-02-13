package cz.hartrik.jregex.dto;

/**
 *
 * @version 2017-02-13
 * @author Patrik Harag
 */
public class Group {

    private final int start;
    private final int end;

    public Group(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public static Group of(int start, int end) {
        return new Group(start, end);
    }

}
