package com.techinterview.edtsbackend.helper.apiResponse;

import com.techinterview.edtsbackend.helper.ErrorCode;

public class Error {
    private String message;
    private ErrorCode code;

    public Error(ErrorCode code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }
}
