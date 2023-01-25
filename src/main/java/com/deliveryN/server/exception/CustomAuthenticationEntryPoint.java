package com.deliveryN.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.error(authException.toString());
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401에러
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        JSONObject reponseJson;

        if(authException instanceof BadCredentialsException){
            reponseJson = new JSONObject(CustomBody.builder().message("인증되지 않은 사용자").detail("이메일이나 비밀번호가 유효하지 않습니다").build());
        }
        else {
            reponseJson = new JSONObject(CustomBody.builder().message("인증되지 않은 사용자").detail("토큰의 정보가 유효하지 않습니다").build());
        }
        response.getWriter().print(reponseJson);
    }
}