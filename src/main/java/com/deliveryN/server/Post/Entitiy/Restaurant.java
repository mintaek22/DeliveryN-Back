package com.deliveryN.server.Post.Entitiy;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Restaurant {

    @Id
    private Long restaurantKey;

    private String name;

    private String category;

}
