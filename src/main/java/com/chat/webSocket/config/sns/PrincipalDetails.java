package com.chat.webSocket.config.sns;

import com.chat.webSocket.member.model.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


//Authentication 객체에 저장할 수 있는 유일한 타입이다.
public class PrincipalDetails implements UserDetails, OAuth2User {

private MemberEntity memberEntity;
private Map<String, Object> attributes;

public PrincipalDetails(MemberEntity memberEntity) { // 생성자
   super();
   this.memberEntity = memberEntity;
}

// OAuth2.0 로그인시 사용
public PrincipalDetails(MemberEntity memberEntity, Map<String, Object> attributes) {
   this.memberEntity = memberEntity;
   this.attributes = attributes;
}

public MemberEntity getMemberEntity() {
   return memberEntity;
}

public void setMemberEntity(MemberEntity memberEntity) {
   this.memberEntity = memberEntity;
}

@Override
public String getPassword() {
   return memberEntity.getPassword();
}

@Override
public String getUsername() {
   return memberEntity.getSnsName();
}

@Override
public boolean isAccountNonExpired() { // 계정이 만료됐는지 확인함
   return true; // 최종 접속시간 확인해서 1년 지났으면 false 넣어야함.
}

@Override
public boolean isAccountNonLocked() { // 계정이 잠겼는지 확인
   return true; // 비밀번호 5번이상 틀리면 false 넣어주면 됨.
}

@Override
public boolean isCredentialsNonExpired() {
   return true;
}

@Override
public boolean isEnabled() {
   return true;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() { // 권한 확인
   List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
   authorities.add(new SimpleGrantedAuthority(memberEntity.getRole().name()));
   return authorities; // 여기 유저 정보 전부 다 리턴
}

// OAuth(리소스) 서버로부터 받는 회원정보
@Override
public Map<String, Object> getAttributes() { // 회원정보를 리턴한다.
   return attributes;
}

@Override
public String getName() {
   return "제공자 ID";
}

}
