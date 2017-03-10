package cz.hartrik.jregex.service;

/**
 *
 * @author Patrik Harag
 * @version 2017-03-10
 */
public final class TimeoutCharSequence implements CharSequence {

    private static final int CHECK_EVERY = 1024*1024;
    private static final String ERR_MESSAGE_FORMAT = "Timeout, terminated after %.2f s";

    private final CharSequence string;
    private final long start;
    private final long end;
    private int checkCalled = 0;

    private TimeoutCharSequence(CharSequence string, long start, long end, int called) {
        this.string = string;
        this.checkCalled = called;

        this.start = start;
        this.end = end;
    }

    private void check() {
        if (checkCalled++ % CHECK_EVERY == 0) {
            long time = System.currentTimeMillis();
            if (time > end) {
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
        CharSequence subSequence = string.subSequence(start, end);

        return new TimeoutCharSequence(
                subSequence, this.start, this.end, this.checkCalled);
    }

    @Override
    public int length() {
        return string.length();
    }

    @Override
    public String toString() {
        return string.toString();
    }

    public static TimeoutCharSequence of(CharSequence string, int millis) {
        long start = System.currentTimeMillis();
        long end = start + millis;

        return new TimeoutCharSequence(string, start, end, 0);
    }

}
