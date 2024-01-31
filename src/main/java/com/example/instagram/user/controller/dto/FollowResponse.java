package com.example.instagram.user.controller.dto;

import com.example.instagram.user.entity.Follow;
import com.example.instagram.user.entity.User;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowResponse {

    private String user_id;
    private List<Follow> followList;

    @Builder
    private FollowResponse(final String user_id, final List<Follow> followList) {
        this.user_id = user_id;
        this.followList = followList;
    }

    public static FollowResponse of(User follower) {
        return FollowResponse.builder().user_id(follower.getUser_id())
            .followList(follower.getFollowers()).build();
    }
}
