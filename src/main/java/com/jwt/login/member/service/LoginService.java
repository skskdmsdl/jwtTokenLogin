package com.jwt.login.member.service;

import com.jwt.login.exception.ErrorCode;
import com.jwt.login.exception.JwtLoginApplicationException;
import com.jwt.login.member.model.Member;
import com.jwt.login.member.model.entity.AuthorityEntity;
import com.jwt.login.member.model.entity.MemberEntity;
import com.jwt.login.member.repository.MemberRepository;
import com.jwt.login.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService extends OidcUserService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException(email));
    }

    public String login(String email, String password) {

        // 회원가입 여부 체크
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> new JwtLoginApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("%s not founded", email)));

        // 비밀번호 체크
        if(!encoder.matches(password, memberEntity.getPassword())){
            throw new JwtLoginApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);

        return token;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        return super.loadUser(userRequest);
    }

    public MemberEntity save(MemberEntity memberEntity) {
        return  memberRepository.save(memberEntity);
    }

    public void addAuthority(Long memberSno, String authority){
        memberRepository.findById(memberSno).ifPresent(memberEntity->{
            AuthorityEntity newRole = new AuthorityEntity(memberEntity.getMemberSno(), authority);
            if(memberEntity.getAuthorities() == null){
                HashSet<AuthorityEntity> authorities = new HashSet<>();
                authorities.add(newRole);
                memberEntity.setAuthorities(authorities);
                save(memberEntity);
            }else if(!memberEntity.getAuthorities().contains(newRole)){
                HashSet<AuthorityEntity> authorities = new HashSet<>();
                authorities.addAll(memberEntity.getAuthorities());
                authorities.add(newRole);
                memberEntity.setAuthorities(authorities);
                save(memberEntity);
            }
        });
    }

    public Member loadUser(final MemberEntity oauth2User){
        MemberEntity memberEntity = memberRepository.save(oauth2User);
        addAuthority(oauth2User.getMemberSno(), "ROLE_USER");
//        MemberEntity memberEntity1 = memberRepository.findByProviderId(oauth2User.getProviderId()).get();
//        MemberEntity memberEntity = memberRepository.findByProviderId(oauth2User.getProviderId()).orElseGet(()->{
//            // table 구성에 따른 save
//            addAuthority(oauth2User.getMemberSno(), "ROLE_USER");
//            return memberRepository.save(oauth2User);
//        });

        return Member.fromEntity(memberEntity);
    }

    public String getSnsToken(String email){
        String token = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);
        return token;
    }

}
