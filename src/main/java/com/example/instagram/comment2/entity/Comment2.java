package com.example.instagram.comment2.entity;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment2.controller.dto.Comment2Request;
import com.example.instagram.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Comment2 {

    @Id
    private int comment2_id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonBackReference
    private Comment comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String body;

    @Builder
    public Comment2(int comment2_id, String body, User user, Comment comment) {
        this.comment2_id = comment2_id;
        this.body = body;
        this.user = user;
        this.comment = comment;
    }

    public Comment2() {

    }

    public String getUserId() {
        return user != null ? user.getUser_id() : null;
    }

    public void patchComment2(Comment2Request comment2Request) {
        this.body = comment2Request.getBody();
    }
}
