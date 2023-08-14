package com.techinterview.edtsbackend.advice;


import com.techinterview.edtsbackend.helper.ErrorCode;
import com.techinterview.edtsbackend.helper.apiResponse.Response;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    @ExceptionHandler(value = { ValidationException.class })
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(Response.error(ErrorCode.INVALID_REQUEST_PAYLOAD, ex.getBody().getDetail()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if(HttpStatus.INTERNAL_SERVER_ERROR.equals(statusCode)) {
            return ResponseEntity.internalServerError().body(Response.error(ErrorCode.UNKNOWN_ERROR, null));
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
