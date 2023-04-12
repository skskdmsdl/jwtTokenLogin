package com.jwt.login.board.controller;

import com.jwt.login.board.controller.request.BoardCreateRequest;
import com.jwt.login.board.controller.response.BoardResponse;
import com.jwt.login.board.service.BoardService;
import com.jwt.login.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Response<Page<BoardResponse>> list(Pageable pageable, Authentication authentication) {
        return Response.success(boardService.list(pageable).map(BoardResponse::fromBoard));
    }
}
