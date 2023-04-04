package com.chat.webSocket.member.controller.response;

import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.MemberRole;
import com.chat.webSocket.member.model.entity.MemberEntity;
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
