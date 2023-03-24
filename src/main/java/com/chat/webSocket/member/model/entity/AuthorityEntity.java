package com.chat.webSocket.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="sp_user_authority")
@IdClass(AuthorityEntity.class)
public class AuthorityEntity implements GrantedAuthority {

    @Id
    @Column(name="member_sno")
    private Long memberSno;

    @Id
    private String authority;
}
