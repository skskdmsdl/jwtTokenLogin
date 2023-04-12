package com.jwt.login.member.service;

import com.jwt.login.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    public Member loadUserByUserName(String userName) {
        Member member = new Member();
        return member;
    }
}
