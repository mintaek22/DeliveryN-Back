package com.deliveryN.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;


@RestControllerAdvice
@Slf4j
public class ApiExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> bindException(Exception e){
        ErrorResponse response = ErrorResponse.builder()
                .message("잘못된 요청")
                .detail("적절한 인자값이 들어오지 않았습니다")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exHandle(CustomException e){
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

}
