package com.deliveryN.server.Post.Service;

import com.deliveryN.server.Post.Entitiy.Post;
import com.deliveryN.server.Post.Repository.PostRepository;
import com.deliveryN.server.Post.Utill.Distance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostListService {

    private final PostRepository postRepository;

    public List<Post> getPosts(String category, Double x, Double y){
        List<Post> posts = postRepository.findByCategory(category);
        posts.sort(new Distance(x, y));
        return posts;
    }
}
