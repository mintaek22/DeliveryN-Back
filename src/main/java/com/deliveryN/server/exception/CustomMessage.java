package com.deliveryN.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CustomMessage {

    /* 200 OK : 올바른요청*/
    OK(org.springframework.http.HttpStatus.OK,"올바른 요청","정상적으로 처리되었습니다"),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_EMAIL(org.springframework.http.HttpStatus.BAD_REQUEST,"잘못된 요청", "이메일이 맞지 않습니다"),
    INVALID_restaurant(org.springframework.http.HttpStatus.BAD_REQUEST,"잘못된 요청", "해당 가게가 존재하지 않습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_PASSWORD(org.springframework.http.HttpStatus.UNAUTHORIZED,"인증되지 않은 사용자","비밀번호가 맞지 않습니다"),
    INVALID_User(org.springframework.http.HttpStatus.UNAUTHORIZED,"인증되지 않은 사용자","해당 고객 정보가 없습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    CONFLICT_EMAIL(org.springframework.http.HttpStatus.CONFLICT,"데이터 중복","중복된 이메일 사용자가 있습니다");

    private final org.springframework.http.HttpStatus httpStatus;
    private final String message;
    private final String detail;
}
