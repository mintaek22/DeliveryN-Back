package com.deliveryN.server.Post.Repository;

import com.deliveryN.server.Post.Entitiy.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query(value = "select p from Post p Restaurant r " +
            "where p.restaurantKey = r.restaurantKey" +
            "and r.category = :category",nativeQuery = true)
    List<Post> findByCategory(@Param("category") String category);
}
