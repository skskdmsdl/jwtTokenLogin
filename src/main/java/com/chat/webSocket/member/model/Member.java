package com.chat.webSocket.member.model;

import com.chat.webSocket.member.model.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Member {
    private Long memberSno;
    private String memberName;
    private String password;
    private MemberRole role;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;


    // entity를 dto로 변환해주는 method
    public static Member fromEntity(MemberEntity entity) {
        return new Member(
                entity.getMemberSno(),
                entity.getMemberId(),
                entity.getPassword(),
                entity.getRole(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }


}
