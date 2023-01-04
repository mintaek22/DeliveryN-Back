package com.deliveryN.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_EMAIL(HttpStatus.BAD_REQUEST,"잘못된 요청", "이메일이 맞지 않습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"인증되지 않은 사용자","비밀번호가 맞지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    CONFLICT_EMAIL(HttpStatus.CONFLICT,"데이터 중복","중복된 이메일 사용자가 있습니다");

    private final HttpStatus httpStatus;
    private final String message;
    private final String detail;
}
