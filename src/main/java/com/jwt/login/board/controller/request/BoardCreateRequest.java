package com.chat.webSocket.board.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardCreateRequest {
    private String title;
    private String body;
}
