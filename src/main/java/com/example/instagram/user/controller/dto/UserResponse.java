package com.example.instagram.user.controller.dto;

import com.example.instagram.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String user_id;
    private final String name;
    private final String intro;
    private final String user_image;

    @Builder
    private UserResponse(final String user_id, final String name, final String intro,
        final String user_image) {
        this.user_id = user_id;
        this.name = name;
        this.intro = intro;
        this.user_image = user_image;
    }

    public static UserResponse of(User user) {
        return UserResponse.builder().user_id(user.getUser_id()).name(user.getName())
            .intro(user.getIntro()).user_image(user.getUser_image())
            .build();
    }
}
