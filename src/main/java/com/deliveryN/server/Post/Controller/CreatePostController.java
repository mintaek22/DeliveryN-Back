package com.deliveryN.server.Post.Controller;

import com.deliveryN.server.Jwt.TokenProvider;
import com.deliveryN.server.Post.Dto.CreatePostDto;
import com.deliveryN.server.Post.Service.CreatePostService;
import com.deliveryN.server.Post.Service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreatePostController {

    private final CreatePostService createPostService;
    private final RestaurantService restaurantService;
    private final TokenProvider tokenProvider;


    //제안 N 작성 요청
    @PostMapping("/post")
    public ResponseEntity<Object> createPost(@Validated @RequestBody CreatePostDto postDto, HttpServletRequest request) throws ParseException {

        String jwt = tokenProvider.resolveToken(request);
        String email = tokenProvider.getId(jwt);

        createPostService.post(postDto,email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //모든 식당 리스트 다 가져오기
    @GetMapping("/restaurants/name")
    public ResponseEntity<List<String>> getRestaurants(){
        List<String> restaurants =  restaurantService.getRestaurantName();
        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }


}
