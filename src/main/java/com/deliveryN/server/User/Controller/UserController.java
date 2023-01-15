package com.deliveryN.server.User.Controller;

import com.deliveryN.server.Jwt.TokenProvider;
import com.deliveryN.server.User.Dto.Token.TokenDto;
import com.deliveryN.server.User.Dto.User.*;
import com.deliveryN.server.User.Dto.result.ResultDto;
import com.deliveryN.server.User.Entity.User;
import com.deliveryN.server.User.Service.UserService;
import com.deliveryN.server.exception.CustomBody;
import com.deliveryN.server.exception.CustomException;
import com.deliveryN.server.exception.CustomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //인증 못받는 유저 /user
    //받은 유저 /

    //헤더가 jwt가 유효하면
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/login")
    public ResponseEntity<Object> autoLogin(HttpServletRequest request){
        //헤더에 있는 토큰 정보를 가지고 온다
        String jwt = tokenProvider.resolveToken(request);
        String email = tokenProvider.getId(jwt);
        Optional<User> user = userService.LoginCheck(email);

        return new ResponseEntity<>(new UserInfoDto(user.get().getNickName()), org.springframework.http.HttpStatus.OK);
    }
    @GetMapping("/user/login")
    public ResponseEntity<Object> signIn(@Validated LoginDto user){

        Optional<User> userCheck = userService.LoginCheck(user.getEmail());

        if (userCheck.isPresent()) {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userCheck.get().getEmail(), user.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);
            log.info(jwt);

            return new ResponseEntity<>(new TokenDto(jwt),HttpStatus.OK);
        }

        //존재하지 않는 이메일
        throw new CustomException(CustomMessage.INVALID_EMAIL);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<Object> signup(@Validated @RequestBody SignUpDto user) {

        Optional<User> userCheck = userService.LoginCheck(user.getEmail());

        if (userCheck.isPresent()) {
            throw new CustomException(CustomMessage.CONFLICT_EMAIL);
        }
        userService.Register(user);

        return new ResponseEntity<>(new CustomBody(),HttpStatus.OK);
    }

    //이메일 중복확인
    @GetMapping("/user/email/check")
    public ResponseEntity<Object> emailCheck(@Validated EmailCheckDto email) {
        if (userService.LoginCheck(email.getEmail()).isEmpty()) {
            return new ResponseEntity<>(new ResultDto(false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResultDto(true), HttpStatus.CONFLICT);
    }

    //비밀번호 중복 체크
    @GetMapping("/user/password/check")
    public ResponseEntity<ResultDto> passwordCheck(@Validated PasswordCheckDto password) {

        if (userService.PasswordCheck(password.getPassword(), password.getPasswordCheck())) {
            return new ResponseEntity<>(new ResultDto(true), HttpStatus.OK);
        }
        //비밀번호 안맞음
        return new ResponseEntity<>(new ResultDto(false),HttpStatus.CONFLICT);
    }

    @PutMapping("/password")
    public ResponseEntity<ResultDto> passwordChange(@Validated @RequestBody LoginDto user) {

        userService.PasswordChange(user.getEmail(), user.getPassword());

        return new ResponseEntity<>(new ResultDto(true),HttpStatus.OK);
    }


}
