package com.deliveryN.server.Post.Repository;

import com.deliveryN.server.Post.Entitiy.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

    @Query("select r.name from Restaurant r")
    List<String> findNameList();

    @Query("select r.restaurantKey from Restaurant r")
    Optional<Restaurant> findByName(String name);

}
