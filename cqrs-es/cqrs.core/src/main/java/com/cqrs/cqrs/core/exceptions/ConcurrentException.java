package com.cqrs.cqrs.core.exceptions;

public class ConcurrentException extends RuntimeException {
    public ConcurrentException(String e) {
        super(e);
    }
}
