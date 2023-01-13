package com.deliveryN.server.Post.Service;

import com.deliveryN.server.Post.Entitiy.Restaurant;
import com.deliveryN.server.Post.Repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<String> getRestaurantName(){
        return restaurantRepository.findNameList();
    }
}
