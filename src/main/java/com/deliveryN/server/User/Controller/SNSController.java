package com.deliveryN.server.User.Controller;

import com.deliveryN.server.Jwt.JwtFilter;
import com.deliveryN.server.Jwt.TokenProvider;
import com.deliveryN.server.User.Dto.Token.TokenDto;
import com.deliveryN.server.User.Dto.User.SignUpDto;
import com.deliveryN.server.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class SNSController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;



    /**
     * 1.프론트에서 해당 url로 이동하면 리다이렉트 https://kauth.kakao.com/oauth/authorize?client_id=fdf1afb684e900ba689ebcca872a41b6&redirect_uri=http://localhost:8080/api/oauth/kakao&response_type=code
     * 2.카카오 정해진 인증을 걸치면 아래 컨트롤러로 인가코드와 함께 들어온다
     * 3.인가코드를 이용하여 access 토큰을 카카오한테 받는다
     * 4.발급받은 토큰으로 가입처리를 한다
     */
    @GetMapping("/kakao")
    public void Kakao(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://kauth.kakao.com/oauth/authorize?client_id=fdf1afb684e900ba689ebcca872a41b6&redirect_uri=http://localhost:8080/api/oauth/kakao&response_type=code");
    }


    @GetMapping("/oauth/kakao")
    public ResponseEntity<TokenDto> registerFromKakao(@RequestParam(name = "code") String code) throws IOException, InterruptedException {

        String RestApiKey="fdf1afb684e900ba689ebcca872a41b6";
        String RedirectUri = "http://localhost:8080/api/oauth/kakao";

        //카카오에게 인가 코드를주어 토큰 받아오기
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://kauth.kakao.com/oauth/token"))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"+
                                "&client_id="+RestApiKey+
                                "&redirect_uri="+RedirectUri+
                                "&code="+code))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String oauth_code =response.body();

        JSONObject jsonObject = new JSONObject(oauth_code);
        String token = jsonObject.getString("access_token");

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://kapi.kakao.com/v2/user/me"))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response2 = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject2 = new JSONObject(response2.body());

        SignUpDto user = new SignUpDto();
        String id_token = String.valueOf(jsonObject2.getInt("id"));

        JSONObject properties = jsonObject2.getJSONObject("properties");
        String nickname = properties.getString("nickname");

        JSONObject kakao_account = jsonObject2.getJSONObject("kakao_account");
        String email = kakao_account.getString("email");

        user.setEmail(email+"K");
        user.setNickName(nickname);
        user.setPassword(id_token);

        //user가 존재하면 로그인하고 아니면 회원가입시키기
        if(userService.LoginCheck(user.getEmail()).isEmpty()){
            userService.Register(user);
        }

        //토큰리턴하기
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getEmail(),id_token);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        log.info(jwt);

        return new ResponseEntity<>(new TokenDto(jwt), HttpStatus.OK);
    }

    /**
     * 1.프론트에서 https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=viIJ4anvkBbvMjklGcP2&state=651651651651&redirect_uri=http://localhost:8080/api/oauth/naver 들어간다
     * 2.네이버에서 정해진 인증을 걸치면 아래 컨트롤러로 인가코드와 함께 들어온다
     * 3.인가코드를 이용하여 access 토큰을 네이버한테 받는다
     * 4.발급받은 토큰으로 가입처리를 한다
     */
    @GetMapping("/naver")
    public void naver(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=viIJ4anvkBbvMjklGcP2&state=651651651651&redirect_uri=http://localhost:8080/api/oauth/naver");
    }

    @GetMapping("/oauth/naver")
    public ResponseEntity<TokenDto> registerFromNaver(@RequestParam(name = "code") String code,@RequestParam(name = "state") String state) throws IOException, InterruptedException {

        String client_id="viIJ4anvkBbvMjklGcP2";
        String client_secret = "F8MMMyw1fw";

        //네이버에게 인가 코드를주어 토큰 받아오기
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://nid.naver.com/oauth2.0/token"))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"+
                                "&client_id="+client_id+
                                "&client_secret="+client_secret+
                                "&code="+code+
                                "&state"+state))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String oauth_code = response.body();

        JSONObject jsonObject = new JSONObject(oauth_code);
        String token = jsonObject.getString("access_token");

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://openapi.naver.com/v1/nid/me"))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization","Bearer "+token)
                .GET()
                .build();

        HttpResponse<String> response2 = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());

        SignUpDto user = new SignUpDto();
        JSONObject jsonObject2 = new JSONObject(response2.body());
        JSONObject properties = jsonObject2.getJSONObject("response");

        String id_token = properties.getString("id");
        String nickname = properties.getString("nickname");
        String email = properties.getString("email");

        user.setEmail(email+"N");
        user.setNickName(nickname);
        user.setPassword(id_token);

        //user가 존재하면 로그인하고 아니면 회원가입시키기
        if(userService.LoginCheck(user.getEmail()).isEmpty()){
            userService.Register(user);
        }

        //토큰리턴하기
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getEmail(),id_token);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        log.info(jwt);

        return new ResponseEntity<>(new TokenDto(jwt), HttpStatus.OK);
    }


}
