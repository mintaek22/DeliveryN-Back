package com.deliveryN.server.Post.Entitiy;

import com.deliveryN.server.Member.Entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PostId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="userId")
    private final Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="restaurantKey")
    private final Restaurant restaurant;

    private final Integer count;

    private final Timestamp deadLine;

    //위도 경도
    private final Double x;

    private final Double y;

    @Builder
    public Post(Member member, Restaurant restaurant, Integer count, Timestamp deadLine, Double x, Double y) {
        this.member = member;
        this.restaurant = restaurant;
        this.count = count;
        this.deadLine = deadLine;
        this.x = x;
        this.y = y;
    }

    
}
