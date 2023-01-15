package com.deliveryN.server.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CustomBody {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String detail;

    public static ResponseEntity<CustomBody> toResponseEntity(CustomMessage errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CustomBody.builder()
                        .message(errorCode.getMessage())
                        .detail(errorCode.getDetail())
                        .build()
                );
    }

    @Builder
    public CustomBody(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public CustomBody() {
        this.message = "올바른 요청";
        this.detail = "정상적으로 처리되었습니다";
    }
}
