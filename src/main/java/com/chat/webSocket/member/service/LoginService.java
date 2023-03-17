package com.chat.webSocket.member.service;

import com.chat.webSocket.WebSocketApplication;
import com.chat.webSocket.exception.ErrorCode;
import com.chat.webSocket.exception.WebSocketApplicationException;
import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.repository.MemberRepository;
import com.chat.webSocket.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public String login(String email, String password) {

        // 회원가입 여부 체크
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> new WebSocketApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not founded", email)));

        // 비밀번호 체크
        if(!encoder.matches(password, memberEntity.getPassword())){
            throw new WebSocketApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);

        return token;
    }
}
