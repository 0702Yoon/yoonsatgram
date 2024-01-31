package com.example.instagram.feed.controller.dto;

import com.example.instagram.feed.entity.Feed;
import com.example.instagram.user.entity.User;
import lombok.Getter;

@Getter
public class FeedRequest {


    private String feed_text;
    private String user_id;

    public Feed toEntity(User user) {
        return Feed.builder().feed_text(this.feed_text).user(user).build();
    }


}
