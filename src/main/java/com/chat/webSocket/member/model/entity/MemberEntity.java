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

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Builder.Default
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    @Column(name = "sns_name")
    private String snsName;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

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

}
