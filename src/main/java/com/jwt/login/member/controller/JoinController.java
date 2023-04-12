package com.jwt.login.member.controller;

import com.jwt.login.member.controller.request.MemberJoinRequest;
import com.jwt.login.member.controller.response.MemberJoinResponse;
import com.jwt.login.member.model.Member;
import com.jwt.login.member.service.JoinService;
import com.jwt.login.utils.Response;
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
        Member member = joinService.join(request.getEmail(), request.getPassword());
        return Response.success(MemberJoinResponse.fromMember(member));
    }
}
