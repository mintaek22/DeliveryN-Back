package com.deliveryN.server.Post.Dto;

import com.deliveryN.server.Post.Entitiy.Post;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PostResponseDto {

    private final String name;

    private final Integer count;

    private final Timestamp deadLine;

    private final Double x;

    private final Double y;

    public PostResponseDto(Post post){
        this.name = post.getRestaurant().getName();
        this.count = post.getCount();
        this.deadLine = post.getDeadLine();
        this.x = post.getX();
        this.y = post.getY();
    }
}
