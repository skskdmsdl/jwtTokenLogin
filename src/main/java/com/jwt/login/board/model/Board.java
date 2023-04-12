package com.jwt.login.board.model;

import com.jwt.login.board.model.entity.BoardEntity;
import com.jwt.login.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Board {
    private Long boardSno;
    private String title;
    private String body;
    private Member member;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // entity를 dto로 변환해주는 method
    public static Board fromEntity(BoardEntity entity) {
        return new Board(
                entity.getBoardSno(),
                entity.getTitle(),
                entity.getBody(),
                Member.fromEntity(entity.getMember()),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
