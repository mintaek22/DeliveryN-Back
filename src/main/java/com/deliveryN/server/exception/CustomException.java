package com.deliveryN.server.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private final ErrorCode  errorCode;

}
