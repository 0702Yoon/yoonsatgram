package com.example.instagram.comment.entity;

import com.example.instagram.comment.controller.dto.CommentRequest;
import com.example.instagram.comment2.entity.Comment2;
import com.example.instagram.feed.entity.Feed;
import com.example.instagram.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public String getUserId() {
        return user.getUser_id();
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;
    private String body;

    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment2> comment2s = new ArrayList<>();

    @Builder
    public Comment(int comment_id, User user, Feed feed, String body) {
        this.comment_id = comment_id;
        this.user = user;
        this.feed = feed;
        this.body = body;
    }

    public Comment() {

    }


    public void patchComment(CommentRequest commentRequest) {
        this.body = commentRequest.getBody();
    }
}
