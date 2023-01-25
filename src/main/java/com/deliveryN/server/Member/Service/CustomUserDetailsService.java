package com.deliveryN.server.Member.Service;


import com.deliveryN.server.Member.Entity.Member;
import com.deliveryN.server.Member.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//유저의 디테일한정보를 반환해주는 클래스
@Service("UserDetailsService")
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {

        Optional<Member> member= memberRepository.findByEmail(email);
        if(member.isEmpty()){
            throw new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.");
        }

        //member에 있는 권한정보를 꺼내와서 주입시킴
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.get().getRole()));


        return new MemberContext(member.get(),roles);
    }


}
