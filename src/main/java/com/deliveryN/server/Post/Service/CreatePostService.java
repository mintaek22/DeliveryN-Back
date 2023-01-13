package com.deliveryN.server.Post.Service;

import com.deliveryN.server.Post.Dto.CreatePostDto;
import com.deliveryN.server.Post.Entitiy.Post;
import com.deliveryN.server.Post.Entitiy.Restaurant;
import com.deliveryN.server.Post.Repository.PostRepository;
import com.deliveryN.server.Post.Repository.RestaurantRepository;
import com.deliveryN.server.User.Entity.User;
import com.deliveryN.server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public void post(CreatePostDto postDto,String email) throws ParseException {

        String str = postDto.getTime();

        String month = str.substring(0,2);
        String day = str.substring(2,4);
        String hour = str.substring(4,6);
        String min = str.substring(6,8);

        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

        Date date = format.parse(month+"-"+day+" "+hour+":"+min);
        java.sql.Date result =  new java.sql.Date(date.getTime());

        Optional<Restaurant> restaurant = restaurantRepository.findByName(postDto.getName());
        Optional<User> user = userRepository.findByEmail(email);

        if(restaurant.isEmpty()){
            //TODO 에러처리
        }

        if(user.isEmpty()){
            //TODO 에러처리
        }

        Post post = new Post();
        post.setUser(user.get());
        post.setRestaurant(restaurant.get());
        post.setCount(postDto.getCount());
        post.setDeadLine(result);
        post.setX(post.getX());
        post.setY(post.getY());

        postRepository.save(post);
    }

}
