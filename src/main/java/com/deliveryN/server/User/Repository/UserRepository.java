package com.deliveryN.server.User.Repository;

import com.deliveryN.server.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findById(String id);
}
