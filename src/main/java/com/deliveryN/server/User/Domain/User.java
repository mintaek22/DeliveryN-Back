package com.deliveryN.server.User.Domain;

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
    private long key;

    private String id;

    private String email;

    private String password;

    private String nickName;

    private int point = 0;

    private String platform;

}
