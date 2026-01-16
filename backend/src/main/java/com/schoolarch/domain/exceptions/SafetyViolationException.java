package com.schoolarch.domain.exceptions;

public class SafetyViolationException extends RuntimeException {
    private final String code;

    public SafetyViolationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() { return code; }
}
