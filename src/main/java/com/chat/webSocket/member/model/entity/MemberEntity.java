package com.chat.webSocket.member.model.entity;

import com.chat.webSocket.member.model.MemberRole;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class MemberEntity implements UserDetails {

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

//    @Column(name = "authority")
//    private AuthorityEntity authority;

    @Column(name = "name")
    private String name;

    @Column(name = "provider")
    private OAuth2Provider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_sno", foreignKey = @ForeignKey(name="member_sno"))
    private Set<AuthorityEntity> authorities;

    @PrePersist
    void registered() {
        this.registeredAt = LocalDateTime.now();
    }

    @PreUpdate
    void updated() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static enum OAuth2Provider {
        google {
            public MemberEntity convert(OAuth2User user){
                return MemberEntity.builder()
                        .providerId(format("%s_%s", name(), user.getAttribute("sub")))
                        .provider(google)
                        .email(user.getAttribute("email"))
                        .name(user.getAttribute("name"))
                        .build();
            }
        },
        naver {
            public MemberEntity convert(OAuth2User user){
                Map<String, Object> resp = user.getAttribute("response");
                return MemberEntity.builder()
                        .providerId(format("%s_%s", name(), resp.get("id")))
                        .provider(naver)
                        .email(""+resp.get("email"))
                        .name(""+resp.get("name"))
                        .build();
            }
        };
        public abstract MemberEntity convert(OAuth2User user);
    }

    public void setAuthorities (Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

}
