package com.jwt.login.board.service;

import com.jwt.login.board.model.Board;
import com.jwt.login.board.model.entity.BoardEntity;
import com.jwt.login.board.repository.BoardRepository;
import com.jwt.login.exception.ErrorCode;
import com.jwt.login.exception.JwtLoginApplicationException;
import com.jwt.login.member.model.entity.MemberEntity;
import com.jwt.login.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new JwtLoginApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("email is %s", email)));

        boardRepository.save(BoardEntity.builder()
                .title(title)
                .body(body)
                .member(memberEntity)
                .build());
    }

    public Page<Board> list(Pageable pageable) {
        return boardRepository.findAll(pageable).map(Board::fromEntity);
    }

    public Board modify(String title, String body, String email, Integer boardId) {
        MemberEntity memberEntity = getMemberEntityOrException(email);
        // board exist
        BoardEntity boardEntity = getBoardEntityOrException(boardId);

        // board permission
        if(boardEntity.getMember() != memberEntity) {
            throw new JwtLoginApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", email, boardId));
        }

        boardEntity.updateBoard(title, body);

        return  Board.fromEntity(boardEntityRepository.saveAndFlush(boardEntity));
    }
}
