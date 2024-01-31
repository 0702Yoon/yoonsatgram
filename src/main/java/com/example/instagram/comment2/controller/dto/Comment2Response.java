package com.example.instagram.comment2.controller.dto;

import com.example.instagram.comment2.entity.Comment2;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment2Response {

    private String user_id;
    private String body;

    @Builder
    private Comment2Response(final String user_id, final String body) {
        this.user_id = user_id;
        this.body = body;
    }

    public static Comment2Response of(Comment2 comment2) {
        return Comment2Response.builder().user_id(comment2.getUserId()).body(comment2.getBody())
            .build();
    }
}
