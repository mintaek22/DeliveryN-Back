package com.deliveryN.server.Post.Service;

import com.deliveryN.server.Post.Dto.PostResponseDto;
import com.deliveryN.server.Post.Entitiy.Post;
import com.deliveryN.server.Post.Repository.PostRepository;
import com.deliveryN.server.Post.Utill.Distance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostListService {

    private final PostRepository postRepository;

    public List<PostResponseDto> getPostsByCategory(String category, Double x, Double y){
        List<Post> posts = postRepository.FindByCategory(category);
        posts.sort(new Distance(x,y));
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    public List<PostResponseDto> getPostsByName(String name, Double x, Double y){
        List<Post> posts = postRepository.FindByName(name);
        posts.sort(new Distance(x,y));
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
}
