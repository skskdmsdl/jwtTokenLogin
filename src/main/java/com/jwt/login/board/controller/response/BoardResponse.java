package com.jwt.login.board.controller.response;

import com.jwt.login.board.model.Board;
import com.jwt.login.member.controller.response.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BoardResponse {
    private Long boardSno;
    private String title;
    private String body;
    private MemberResponse member;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static BoardResponse fromBoard(Board board){
        return new BoardResponse(
                board.getBoardSno(),
                board.getTitle(),
                board.getBody(),
                MemberResponse.fromMember(board.getMember()),
                board.getRegisteredAt(),
                board.getUpdatedAt(),
                board.getDeletedAt()
        );
    }
}
