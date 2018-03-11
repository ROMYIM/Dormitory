package com.john_yim.dormitory.entity;

/**
 * Created by MSI-PC on 2018/2/15.
 */

public class ResponseResult<T> {
    private int code;
    private T result;
    private String message;

    public ResponseResult() {

    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

}
