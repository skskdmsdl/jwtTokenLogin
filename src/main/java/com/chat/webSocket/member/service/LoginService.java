package com.chat.webSocket.member.service;

import com.chat.webSocket.WebSocketApplication;
import com.chat.webSocket.exception.ErrorCode;
import com.chat.webSocket.exception.WebSocketApplicationException;
import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public String login(String memberId, String password) {

        // 회원가입 여부 체크
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId).orElseThrow(() -> new WebSocketApplicationException(ErrorCode.DUPLICATED_MEMBER_ID, ""));

        // 비밀번호 체크
        if(!memberEntity.getPassword().equals(password)) {
            throw new WebSocketApplicationException(ErrorCode.DUPLICATED_MEMBER_ID, "");
        }

        // 토큰 생성


        return "";
    }
}
