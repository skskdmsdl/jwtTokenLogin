package com.chat.webSocket.member.controller.response;

import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.MemberRole;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberJoinResponse {

    private Long memberSno;
    private String memberId;
    private MemberRole role;

    public static MemberJoinResponse fromMember(Member member){
        return new MemberJoinResponse(
                member.getMemberSno(),
                member.getMemberName(),
                member.getRole()
        );
    }

}