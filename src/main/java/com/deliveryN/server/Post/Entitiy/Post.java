package com.deliveryN.server.Post.Entitiy;

import com.deliveryN.server.User.Entity.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PostId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="restaurantKey")
    private Restaurant restaurant;

    private Integer count;

    private Timestamp deadLine;

    //위도 경도
    private Double x;

    private Double y;

    public Post() {
    }


}
