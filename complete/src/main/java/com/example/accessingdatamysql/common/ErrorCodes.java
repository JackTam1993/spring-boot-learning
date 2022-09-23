package com.example.accessingdatamysql.common;

public enum ErrorCodes {

    OK(0, ""),
    USER_EXIST(1, "user exist"),
    NO_SUCH_USER(2, "no such user");

    private int code;
    private String message;

    private ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    };

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
