package com.chat.webSocket.config.sns;

import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String snsName) throws UsernameNotFoundException {
		// 여기 등록해서 PrincipalDetailService 를 던져줌
		// request 시 username, password 받았을 때 데이터베이스에 있는지 없는지 판단함
		Optional<MemberEntity> memberEntity = memberRepository.findBySnsName(snsName);
		if (!memberEntity.isPresent()) {
			return null;
		}
		return new PrincipalDetails(memberEntity.get());
	}
}
