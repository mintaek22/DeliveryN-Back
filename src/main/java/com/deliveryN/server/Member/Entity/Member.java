package com.deliveryN.server.Member.Entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Getter
@Entity
@Transactional
public class Member {

    public Member() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String email;

    private String password;

    private String nickName;

    private Integer point=0;

    private String role;

    @Builder
    public Member(String name,String email, String password, String nickName,String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }
}
