package com.chat.webSocket.member.controller;

import com.chat.webSocket.member.controller.request.MemberJoinRequest;
import com.chat.webSocket.member.controller.response.MemberJoinResponse;
import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.service.JoinService;
import com.chat.webSocket.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("")
    public Response<MemberJoinResponse> join(@RequestBody MemberJoinRequest request) {
        Member member = joinService.join(request.getUserId(), request.getPassword());
        return Response.success(MemberJoinResponse.fromMember(member));
    }
}
