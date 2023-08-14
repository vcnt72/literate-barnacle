package com.techinterview.edtsbackend.helper.apiResponse;

import com.techinterview.edtsbackend.helper.ErrorCode;

public class Response<T> {
    private T data;
    private Error error;

    public Response(T data) {
        this.data = data;
        this.error = null;
    }

    public Response(Error error) {
        this.error = error;
        this.data = null;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }
    public static <T> Response<T> error(ErrorCode errorCode, String message) {
        return new Response<>(new Error(errorCode, message));
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
