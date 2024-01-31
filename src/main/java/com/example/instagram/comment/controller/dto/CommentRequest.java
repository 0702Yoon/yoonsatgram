package com.example.instagram.comment.controller.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.feed.entity.Feed;
import com.example.instagram.user.entity.User;
import lombok.Getter;

@Getter
public class CommentRequest {

    private int comment_id;
    private String user_id;
    private String body;


    public Comment toEntity(User user, Feed feed) {
        return Comment.builder().comment_id(this.comment_id).user(user).feed(feed)
            .body(this.body).build();
    }
}
