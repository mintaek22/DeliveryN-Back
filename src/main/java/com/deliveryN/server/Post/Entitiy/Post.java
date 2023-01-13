package com.deliveryN.server.Post.Entitiy;

import com.deliveryN.server.User.Entity.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurantKey")
    private Restaurant restaurant;

    private String category;

    private Integer count;

    private Date deadLine;

    //위도 경도
    private Double x;

    private Double y;

    public Post() {
    }
}
