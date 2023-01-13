package com.deliveryN.server.Post.Entitiy;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Restaurant {

    @Id
    private Long restaurantKey;

    private String name;

    private String category;

}
