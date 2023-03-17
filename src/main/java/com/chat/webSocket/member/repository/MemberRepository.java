package com.chat.webSocket.member.repository;

import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);

    Optional<Member> findBySnsName(String username);

}
