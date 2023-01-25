package com.deliveryN.server.Member.Controller;

import com.deliveryN.server.Jwt.TokenProvider;
import com.deliveryN.server.Member.Dto.Member.*;
import com.deliveryN.server.Member.Dto.Token.TokenDto;
import com.deliveryN.server.Member.Dto.result.ResultDto;
import com.deliveryN.server.Member.Entity.Member;
import com.deliveryN.server.Member.Service.MemberService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final TokenProvider tokenProvider;
    private final MemberService memberservice;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    //인증 못받는 유저 /user
    //받은 유저 /

    //헤더가 jwt가 유효하면
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/login")
    public ResponseEntity<Object> autoLogin(HttpServletRequest request){
        //헤더에 있는 토큰 정보를 가지고 온다
        String jwt = tokenProvider.resolveToken(request);
        String email = tokenProvider.getId(jwt);
        Optional<Member> user = memberservice.LoginCheck(email);

        return new ResponseEntity<>(new MemberInfoDto(user.get().getNickName()), org.springframework.http.HttpStatus.OK);
    }
    @GetMapping("/user/login")
    public ResponseEntity<Object> signIn(@Validated LoginDto member){

        //인증용 토큰 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());

        //토큰을 통해 유효한지 검사한다
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String jwt = tokenProvider.createToken(authentication);

        return new ResponseEntity<>(new TokenDto(jwt),HttpStatus.OK);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<Object> signup(@Validated @RequestBody SignUpDto member) {

        Optional<Member> userCheck = memberservice.LoginCheck(member.getEmail());

        if (userCheck.isPresent()) {
            throw new CustomException(CustomMessage.CONFLICT_EMAIL);
        }
        else {

            String encodePassword = passwordEncoder.encode(member.getPassword());
            Member freshMember = Member.builder().
                    email(member.getEmail()).
                    password(encodePassword).
                    nickName(member.getNickName()).
                    name(member.getName()).
                    role("ROLE_USER").
                    build();

            memberservice.Register(freshMember);

            return new ResponseEntity<>(new CustomBody(),HttpStatus.OK);
        }
    }

    //이메일 중복확인
    @GetMapping("/user/email/check")
    public ResponseEntity<Object> emailCheck(@Validated EmailCheckDto email) {
        if (memberservice.LoginCheck(email.getEmail()).isEmpty()) {
            return new ResponseEntity<>(new ResultDto(false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResultDto(true), HttpStatus.CONFLICT);
    }

    //비밀번호 중복 체크
    @GetMapping("/user/password/check")
    public ResponseEntity<ResultDto> passwordCheck(@Validated PasswordCheckDto password) {

        if (memberservice.PasswordCheck(password.getPassword(), password.getPasswordCheck())) {
            return new ResponseEntity<>(new ResultDto(true), HttpStatus.OK);
        }
        //비밀번호 안맞음
        return new ResponseEntity<>(new ResultDto(false),HttpStatus.CONFLICT);
    }

    @PutMapping("/password")
    public ResponseEntity<ResultDto> passwordChange(@Validated @RequestBody LoginDto user) {

      //  memberservice.PasswordChange(user.getEmail(), user.getPassword());

        return new ResponseEntity<>(new ResultDto(true),HttpStatus.OK);
    }


}
