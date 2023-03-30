package com.chat.webSocket.board.service;

import com.chat.webSocket.board.model.entity.BoardEntity;
import com.chat.webSocket.board.repository.BoardRepository;
import com.chat.webSocket.exception.ErrorCode;
import com.chat.webSocket.exception.WebSocketApplicationException;
import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void create(String email, String title, String body) {
        MemberEntity memberEntity = memberRepository.findByEmail(email)
                .orElseThrow(() -> new WebSocketApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("email is %s", email)));
//        BoardEntity boardEntity = BoardEntity.of(title, body, memberEntity);
//        BoardRepository.save(boardEntity);
    }
}