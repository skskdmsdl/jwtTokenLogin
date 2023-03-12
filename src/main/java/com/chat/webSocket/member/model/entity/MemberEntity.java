package com.chat.webSocket.member.model.entity;

import com.chat.webSocket.member.model.MemberRole;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_sno")
    private Long memberSno;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "password")
    private String password;

    @Builder.Default
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    void registered() {
        this.registeredAt = LocalDateTime.now();
    }

    @PreUpdate
    void updated() {
        this.updatedAt = LocalDateTime.now();
    }




//     MemberEntity를 반환하는 method
//     MemberEntity :실제 db테이블에 매핑되는 클래스 와, Member : dto 클래스가 있음
//     이 두개는 분리가 되는게 좋음
//     Member는 멤버의 정보를 가져와서 서비스 단에서 처리할때는 이 클래스를 사용하는게 좋음
//     db에 저장할때만 MemberEntity 사용하는게 좋음
//     혼용해서 사용하면 jpa를 사용하기에 클래스 자체의 변화에 민감하고,
//     우리가 단순히 dto 필드를 변경(db 에 영향이 없는 작업)하고 싶은 경우 MemberEntity를 사용하면 db 변경이 될 수 있음
//     그렇기에 서비스 단에서는 member dto만 사용하여 데이터를 다룰 예정
//     이를 위한 변환 메소드 작성
//    public static MemberEntity of(String memberId, String password){
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setMemberId(memberId);
//        memberEntity.setPassword(password);
//        return memberEntity;
//    }
}
