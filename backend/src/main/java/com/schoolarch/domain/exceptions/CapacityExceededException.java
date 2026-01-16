package com.schoolarch.domain.exceptions;

public class CapacityExceededException extends RuntimeException {
    private final int max;
    private final int total;

    public CapacityExceededException(int max, int total) {
        super("CapacityExceededException: total=" + total + " max=" + max);
        this.max = max;
        this.total = total;
    }

    public int getMax() { return max; }
    public int getTotal() { return total; }
}
