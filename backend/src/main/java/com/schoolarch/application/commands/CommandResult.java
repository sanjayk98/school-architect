package com.schoolarch.application.commands;

import com.schoolarch.domain.errors.DomainError;

public record CommandResult<T>(
    boolean success,
    T payload,
    DomainError error
) {
    public static <T> CommandResult<T> ok(T payload) {
        return new CommandResult<>(true, payload, null);
    }
    public static <T> CommandResult<T> fail(DomainError error) {
        return new CommandResult<>(false, null, error);
    }
}
