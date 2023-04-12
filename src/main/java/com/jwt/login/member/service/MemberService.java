package com.chat.webSocket.member.service;

import com.chat.webSocket.exception.ErrorCode;
import com.chat.webSocket.member.model.Member;
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
