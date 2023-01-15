package com.deliveryN.server.Post.Repository;

import com.deliveryN.server.Post.Entitiy.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

    @Query("select r.name from Restaurant r")
    List<String> findNameList();

    List<Restaurant> findByName(String name);

}
