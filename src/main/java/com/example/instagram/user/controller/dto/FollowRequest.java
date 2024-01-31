package com.example.instagram.user.controller.dto;

import com.example.instagram.user.entity.Follow;
import com.example.instagram.user.entity.User;
import lombok.Getter;

@Getter
public class FollowRequest {

    private String user_id;
    private String followed_id;

    public Follow toEntity(User follower, User followered) {
        return Follow.builder().follower(follower).followered(followered).build();
    }
}
