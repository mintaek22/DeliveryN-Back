package com.deliveryN.server.User.Entity;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Data
@Entity
@Transactional
public class User {

    public User() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;

    private String email;

    private String password;

    private String nickName;

    private Integer point=0;

}
