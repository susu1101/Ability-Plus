package com.ability_plus.utils;


/**
 * @author sjx
 */
public class RestResponse<T> extends RestSimpleResponse {

    private T data;

    public RestResponse(){}

    RestResponse(Long resultCode, String message, T data) {
        this.data = data;
        this.setResultCode(resultCode);
        this.setMessage(message);
    }

    public static <T> RestResponse<T> success() {
        return success(null);
    }

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(200L, "success", data);
    }

    public static <T> RestResponse<T> error(Long resultCode, Throwable throwable) {
        if (resultCode == null) {
            resultCode = -1L;
        }
        return new RestResponse<>(resultCode, throwable.getMessage(), null);
    }

    public static <T> RestResponse<T> error(Long resultCode, String message) {
        if (resultCode == null) {
            resultCode = -1L;
        }
        return new RestResponse<>(resultCode, message, null);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
