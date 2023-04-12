package com.chat.webSocket.board.model.entity;

import com.chat.webSocket.member.model.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@SQLDelete(sql = "UPDATE board SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSno;

    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT") // columnDefinition = 컬럼 타입 변경
    private String body;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // UserEntity에서 post를 가져올 때 user_id로 user 테이블과 join 해서 가져옴
    @ManyToOne // 하나의 유저가 여러 post 쓸 수 있음
    @JoinColumn(name = "member_sno")
    private MemberEntity member;

    @PrePersist
    void registeredAt() {
        this.registeredAt = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public BoardEntity(String title, String body, MemberEntity member) {
        this.title = title;
        this.body = body;
        this.member = member;
    }

    // board 수정
    public void updateBoard(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
