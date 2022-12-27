package com.deliveryN.server.User.Service;

import com.deliveryN.server.User.Domain.User;
import com.deliveryN.server.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void Register(User user){
        userRepository.save(user);
    }

    public boolean LoginCheck(String id){
        return userRepository.findById(id).size()==0;
    }

}
