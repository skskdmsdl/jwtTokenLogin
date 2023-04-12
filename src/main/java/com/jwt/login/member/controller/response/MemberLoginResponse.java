package com.chat.webSocket.member.controller.response;

import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginResponse {
    private String token;
}
