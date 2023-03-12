package com.chat.webSocket.member.controller;

import com.chat.webSocket.member.controller.request.MemberLoginRequest;
import com.chat.webSocket.member.controller.response.MemberLoginResponse;
import com.chat.webSocket.member.service.LoginService;
import com.chat.webSocket.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("")
    public Response<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        String token = loginService.login(request.getMemberId(), request.getPassword());
        return Response.success(new MemberLoginResponse(token));
    }
}
