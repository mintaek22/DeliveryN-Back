package com.deliveryN.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ApiExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> bindException(BindException e){
        ErrorResponse response = ErrorResponse.builder()
                .message("잘못된 요청")
                .detail("적절한 인자값이 들어오지 않았습니다")
                .build();
        log.error("적절한 인자값이 들어오지 않았습니다");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> HttpMessageNotReadableException(HttpMessageNotReadableException e){
        ErrorResponse response = ErrorResponse.builder()
                .message("잘못된 요청")
                .detail("body 값이 존재하지 않습니다")
                .build();
        log.error("body 값이 존재하지 않습니다");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exHandle(CustomException e){
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

}
