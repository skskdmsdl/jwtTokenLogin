package com.chat.webSocket.member.controller;

import com.chat.webSocket.member.controller.request.MemberLoginRequest;
import com.chat.webSocket.member.controller.response.MemberLoginResponse;
import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.service.LoginService;
import com.chat.webSocket.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("")
    public Response<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        String token = loginService.login(request.getEmail(), request.getPassword());
        return Response.success(new MemberLoginResponse(token));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sns")
    public Response<MemberLoginResponse> snsLogin(@AuthenticationPrincipal Member member){
        String token = loginService.getSnsToken(member.getEmail());
        return Response.success(new MemberLoginResponse(token));
    }
}
