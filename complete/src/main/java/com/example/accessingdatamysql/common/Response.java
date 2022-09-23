package com.example.accessingdatamysql.common;

import lombok.Data;

@Data
public class Response <T> {
    
    private Integer code;
    private String message;
    private T data;

    public static <T> Response ok() {
        Response<T> response = new Response<T>();

        response.setCode(0);
        response.setMessage("success");

        return response;
    }

    public static <T> Response ok(T data) {
        Response<T> response = new Response<T>();

        response.setCode(ErrorCodes.OK.getCode());
        response.setMessage(ErrorCodes.OK.getMessage());
        response.setData(data);

        return response;
    }

    public static <T> Response fail(ErrorCodes errorCodes) {
        Response<T> response = new Response<T>();

        response.setCode(errorCodes.getCode());
        response.setMessage(errorCodes.getMessage());

        return response;
    }

}
