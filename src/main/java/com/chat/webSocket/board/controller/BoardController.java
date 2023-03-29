package com.chat.webSocket.board.controller;

import com.chat.webSocket.board.controller.request.BoardCreateRequest;
import com.chat.webSocket.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

    @PostMapping
    public Response<Void> create(@RequestBody BoardCreateRequest request) {
        return Response.success(null);
    }
}
