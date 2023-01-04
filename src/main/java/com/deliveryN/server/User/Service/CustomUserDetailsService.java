package com.deliveryN.server.User.Service;


import com.deliveryN.server.User.Entity.User;
import com.deliveryN.server.User.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("userDetailsService")
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {

        Optional<User> user= Optional.ofNullable(userRepository.findByEmail(email).get(0));
        if(user.isEmpty()){
            return (UserDetails) new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.");
        }
        return createUser(email,user.get());
    }


    //ROLE_USER 권한을 준다
    private org.springframework.security.core.userdetails.User createUser(String username, User user) {

        //권한 있는 사용자다
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),roles);
    }


}
