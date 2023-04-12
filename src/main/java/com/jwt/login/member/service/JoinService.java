package com.chat.webSocket.member.service;

import com.chat.webSocket.exception.ErrorCode;
import com.chat.webSocket.exception.WebSocketApplicationException;
import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Member join(String email, String password) {

        // 회원가입하려는 email로 회원가입된 member가 있는지 (있다면 exception throw)
        memberRepository.findByEmail(email).ifPresent(it -> {
            throw new WebSocketApplicationException(ErrorCode.DUPLICATED_EMAIL, String.format("%s is duplicated", email));
        });

        // 회원가입 진행 - member 등록
        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                                                        .email(email)
                                                        .password(encoder.encode(password))
                                                        .build());

        return Member.fromEntity(memberEntity);
    }


}
