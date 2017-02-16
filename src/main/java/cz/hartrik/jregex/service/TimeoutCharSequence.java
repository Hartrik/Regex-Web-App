package cz.hartrik.jregex.service;

/**
 *
 * @author Patrik Harag
 * @version 2017-02-16
 */
public final class TimeoutCharSequence implements CharSequence {

    private final CharSequence string;

    private final int timeoutMillis;
    private final long start;
    private final long timeout;

    private int charAtCalled = 0;

    private static final int CHECK = 1024*1024;
    private static final String ERR_MESSAGE_FORMAT = "Timeout, terminated after %.2f s";

    public TimeoutCharSequence(CharSequence string, int timeoutMillis) {
        this(string, timeoutMillis, 0);
    }

    private TimeoutCharSequence(CharSequence inner, int timeoutMillis, int charAtCalled) {
        this.string = inner;

        this.timeoutMillis = timeoutMillis;
        this.start = System.currentTimeMillis();
        this.timeout = start + timeoutMillis;

        this.charAtCalled = charAtCalled;
    }

    private void check() {
        if (charAtCalled++ % CHECK == 0) {
            long time = System.currentTimeMillis();
            if (time > timeout) {
                double s = (double) (time - start) / 1000;
                throw new RuntimeException(String.format(ERR_MESSAGE_FORMAT, s));
            }
        }
    }

    @Override
    public char charAt(int index) {
        check();
        return string.charAt(index);
    }

    @Override
    public TimeoutCharSequence subSequence(int start, int end) {
        return new TimeoutCharSequence(string.subSequence(start, end), timeoutMillis);
    }

    @Override
    public int length() {
        return string.length();
    }

    @Override
    public String toString() {
        return string.toString();
    }
}
