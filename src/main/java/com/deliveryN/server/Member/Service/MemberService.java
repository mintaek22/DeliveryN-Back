package com.deliveryN.server.Member.Service;

import com.deliveryN.server.Member.Entity.Member;
import com.deliveryN.server.Member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    public void Register(Member member){
        memberRepository.save(member);
    }


    public Optional<Member> LoginCheck(String email){
        return memberRepository.findByEmail(email);
    }

    public boolean PasswordCheck(String password,String passwordCheck){
        return Objects.equals(password, passwordCheck);
    }

/*    public void PasswordChange(String email,String newPassword){
        String encodePassword = passwordEncoder.encode(newPassword);
        memberRepository.updatePassword(email,encodePassword);
    }*/

}
