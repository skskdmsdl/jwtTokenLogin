package com.chat.webSocket.board.controller;

import com.chat.webSocket.board.controller.request.BoardCreateRequest;
import com.chat.webSocket.board.service.BoardService;
import com.chat.webSocket.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public Response<Void> create(@RequestBody BoardCreateRequest request, Authentication authentication) {
        boardService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }
}
