package com.ability_plus.system.entity;

public class AppException extends RuntimeException {

    private Long code;
    private Integer httpStatus;

    public AppException(String message) {
        this(message, null, null, null);
    }

    public AppException(String message, Throwable throwable) {
        this(message, null, null, throwable);
    }

    public AppException(String message, Long code) {
        this(message, code, null, null);
    }

    public AppException(String message, Long code, Throwable throwable) {
        this(message, code, null, throwable);
    }

    public AppException(String message, Long code, Integer httpStatus) {
        this(message, code, httpStatus, null);
    }

    public AppException(String message, Long code, Integer httpStatus, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public Long getCode() {
        return code;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }
}

