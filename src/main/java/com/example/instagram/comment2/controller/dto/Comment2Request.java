package com.example.instagram.comment2.controller.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment2.entity.Comment2;
import com.example.instagram.user.entity.User;
import lombok.Getter;

@Getter
public class Comment2Request {

    private int comment2_id;
    private String body;
    private String user_id;

    public Comment2 toEntity(User user, Comment comment) {
        return Comment2.builder().comment2_id(this.comment2_id).body(this.body).user(user)
            .comment(comment).build();
    }
}
