package com.chat.webSocket.board.model;

import com.chat.webSocket.board.model.entity.BoardEntity;
import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.entity.MemberEntity;
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
