package com.deliveryN.server.Member.Repository;

import com.deliveryN.server.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = ?2 WHERE m.email = ?1")
    void updatePassword(String email, String newPassword);

}
