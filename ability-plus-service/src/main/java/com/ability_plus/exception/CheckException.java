package com.ability_plus.exception;

public class CheckException extends AppException {

    public CheckException(String message) {
        super(message,400L);
    }

    public CheckException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CheckException(String message, Long code) {
        super(message, code);
    }

    public CheckException(String message, Long code, Throwable throwable) {
        super(message, code, throwable);
    }

    public CheckException(String message, Long code, Integer httpStatus) {
        super(message, code, httpStatus);
    }

    public CheckException(String message, Long code, Integer httpStatus, Throwable throwable) {
        super(message, code, httpStatus, throwable);
    }
}