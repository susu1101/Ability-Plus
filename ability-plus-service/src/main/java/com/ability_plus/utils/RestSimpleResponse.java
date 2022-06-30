package com.ability_plus.utils;

public class RestSimpleResponse {

    private Long resultCode;
    private String message;

    public Long getStatus() {
        return resultCode;
    }

    public void setStatus(Long status) {
        resultCode = status;
    }

    public void setResultCode(Long resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
