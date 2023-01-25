package com.deliveryN.server.Post.Service;

import com.deliveryN.server.Member.Entity.Member;
import com.deliveryN.server.Member.Repository.MemberRepository;
import com.deliveryN.server.Post.Dto.CreatePostDto;
import com.deliveryN.server.Post.Entitiy.Post;
import com.deliveryN.server.Post.Entitiy.Restaurant;
import com.deliveryN.server.Post.Repository.PostRepository;
import com.deliveryN.server.Post.Repository.RestaurantRepository;
import com.deliveryN.server.exception.CustomException;
import com.deliveryN.server.exception.CustomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostRepository postRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    public void post(CreatePostDto postDto,String email) throws ParseException {

        String str = postDto.getTime();

        LocalDate now = LocalDate.now();

        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonthValue());
        String day = String.valueOf(now.getDayOfMonth());
        String hour = str.substring(0,2);
        String min = str.substring(2,4);

        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.hh.mm");

        Date date = format.parse(year+"."+month+"."+day+"."+hour+"."+min);
        java.sql.Timestamp result =  new java.sql.Timestamp(date.getTime());

        List<Restaurant> restaurant = restaurantRepository.findByName(postDto.getName());
        Optional<Member> member = memberRepository.findByEmail(email);


        if(restaurant.isEmpty()){
            throw new CustomException(CustomMessage.INVALID_restaurant);
        }

        if(member.isEmpty()){
            throw new CustomException(CustomMessage.CONFLICT_USER);
        }

       Post post = Post.builder().
               member(member.get())
               .restaurant(restaurant.get(0))
               .count(postDto.getCount())
               .deadLine(result)
               .x(postDto.getX())
               .y(postDto.getY())
               .build();

        postRepository.save(post);
    }

}
