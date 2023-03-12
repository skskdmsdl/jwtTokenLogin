package com.chat.webSocket.member.service;

import com.chat.webSocket.exception.ErrorCode;
import com.chat.webSocket.exception.WebSocketApplicationException;
import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;

    public Member join(String memberId, String password) {

        // 회원가입하려는 memberId로 회원가입된 member가 있는지 (있다면 exception throw)
        memberRepository.findByMemberId(memberId).ifPresent(it -> {
            throw new WebSocketApplicationException(ErrorCode.DUPLICATED_MEMBER_ID, String.format("%s is duplicated", memberId));
        });

        // 회원가입 진행 - member 등록
        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                                                        .memberId(memberId)
                                                        .password(password)
                                                        .build());

        return Member.fromEntity(memberEntity);
    }


}
