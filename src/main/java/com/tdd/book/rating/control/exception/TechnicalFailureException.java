package com.tdd.book.rating.control.exception;

public class TechnicalFailureException extends RuntimeException {
    public TechnicalFailureException(String message) {
        super(message);
    }
}
