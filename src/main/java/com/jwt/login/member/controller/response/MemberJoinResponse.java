package com.jwt.login.member.controller.response;

import com.jwt.login.member.model.Member;
import com.jwt.login.member.model.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinResponse {

    private Long memberSno;
    private String email;
    private MemberRole role;

    public static MemberJoinResponse fromMember(Member member){
        return new MemberJoinResponse(
                member.getMemberSno(),
                member.getEmail(),
                member.getRole()
        );
    }

}
