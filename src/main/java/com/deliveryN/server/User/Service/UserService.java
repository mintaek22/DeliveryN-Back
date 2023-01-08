package com.deliveryN.server.User.Service;

import com.deliveryN.server.User.Dto.User.SignUpDto;
import com.deliveryN.server.User.Entity.User;
import com.deliveryN.server.User.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void Register(SignUpDto user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        User freshUser = new User();
        freshUser.setEmail(user.getEmail());
        freshUser.setPassword(encodePassword);
        freshUser.setNickName(user.getNickName());
        freshUser.setName(user.getName());
        userRepository.save(freshUser);
    }

    public Optional<User> LoginCheck(String email){
        if (userRepository.findByEmail(email).size()!=0){
            return Optional.ofNullable(userRepository.findByEmail(email).get(0));
        }
        return Optional.empty();
    }

    public boolean PasswordCheck(String password,String passwordCheck){
        return Objects.equals(password, passwordCheck);
    }

    @Transactional
    public void PasswordChange(String email,String newPassword){
        String encodePassword = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(email,encodePassword);
    }

}
