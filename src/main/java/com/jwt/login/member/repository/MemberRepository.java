package com.jwt.login.member.repository;

import com.jwt.login.member.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByName(String name);

    Optional<MemberEntity> findByProviderAndProviderId(String provider, String providerId);

    Optional<MemberEntity> findByProviderId(String providerId);
}
