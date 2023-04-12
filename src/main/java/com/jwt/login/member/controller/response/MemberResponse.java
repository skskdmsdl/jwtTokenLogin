package com.jwt.login.member.controller.response;

import com.jwt.login.member.model.Member;
import com.jwt.login.member.model.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {
    private String email;
    private String userName;
    private MemberRole role;

    public static MemberResponse fromMember(Member member){
        return new MemberResponse(
                member.getEmail(),
                member.getName(),
                member.getRole()
        );
    }
}
