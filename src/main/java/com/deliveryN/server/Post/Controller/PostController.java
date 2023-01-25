package com.deliveryN.server.Post.Controller;

import com.deliveryN.server.Post.Dto.PostResponseDto;
import com.deliveryN.server.Post.Service.PostListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostListService postListService;
    private final static String[] category_list = new String[] {"치킨","피자/양식","한식","중식","일식/돈까스","족발/보쌈","분식"};

    /**거리순
     *카테고리 0,1,2,3,4,5,6
     */
    @GetMapping("/post/{category}")
    public ResponseEntity<List<PostResponseDto>> getPost(@PathVariable("category")Integer category, Double x, Double y){

        String category_name = category_list[category];

        List<PostResponseDto> posts = postListService.getPostsByCategory(category_name,x,y);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostResponseDto>> getPostByName(String name, Double x, Double y){
        List<PostResponseDto> posts = postListService.getPostsByName(name,x,y);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
