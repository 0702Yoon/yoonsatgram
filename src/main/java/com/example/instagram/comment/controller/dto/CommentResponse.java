package com.example.instagram.comment.controller.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment2.entity.Comment2;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {

    private final String user_id;
    private final String body;
    private final List<Comment2> comment2List;

    @Builder
    private CommentResponse(final String user_id, final String body,
        final List<Comment2> comment2List) {
        this.user_id = user_id;
        this.body = body;
        this.comment2List = comment2List;
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder().user_id(comment.getUserId())
            .body(comment.getBody()).comment2List(comment.getComment2s()).build();
    }

}
