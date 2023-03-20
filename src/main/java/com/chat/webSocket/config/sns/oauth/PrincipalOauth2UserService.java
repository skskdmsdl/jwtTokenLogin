package com.chat.webSocket.config.sns.oauth;

import com.chat.webSocket.config.sns.PrincipalDetails;
import com.chat.webSocket.config.sns.oauth.provider.FaceBookUserInfo;
import com.chat.webSocket.config.sns.oauth.provider.GoogleUserInfo;
import com.chat.webSocket.config.sns.oauth.provider.OAuth2UserInfo;
import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private MemberRepository memberRepository;

	// userRequest 는 code를 받아서 accessToken을 응답 받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회

		// code를 통해 구성한 정보
//		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
		// token을 통해 응답받은 회원정보
//		System.out.println("oAuth2User : " + oAuth2User);

		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
		} else {
			System.out.println("우리는 구글과 페이스북만 지원해요 ㅎㅎ");
		}

		System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
		System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
		Optional<MemberEntity> userOptional = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		MemberEntity memberEntity;
		
		if (userOptional.isPresent()) {
			memberEntity = userOptional.get();
		} else {
			// member의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
			memberEntity = MemberEntity.builder()
					.snsName(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail())
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId()).build();
			memberRepository.save(memberEntity);
		}
		return new PrincipalDetails(memberEntity, oAuth2User.getAttributes());
	}
}