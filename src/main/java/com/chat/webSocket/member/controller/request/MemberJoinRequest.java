package com.chat.webSocket.member.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinRequest {
    private String userId;
    private String password;
}
