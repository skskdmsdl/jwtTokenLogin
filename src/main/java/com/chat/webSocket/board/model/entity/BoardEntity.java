package com.chat.webSocket.board.model.entity;

import com.chat.webSocket.member.model.entity.MemberEntity;
import lombok.Getter;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "board")
@Getter
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_sno;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT") // columnDefinition = 컬럼 타입 변경
    private String body;

    // UserEntity에서 post를 가져올 때 user_id로 user 테이블과 join 해서 가져옴
    @ManyToOne // 하나의 유저가 여러 post 쓸 수 있음
    @JoinColumn(name = "member_sno")
    private MemberEntity member;
}
