package com.example.instagram.user.controller.dto;

import com.example.instagram.user.entity.User;
import java.util.Optional;
import lombok.Getter;


@Getter
public class UserRequest {

    private final String DEFAULT_IMAGE_URL = "";

    private String user_id;
    private String name;
    private String intro;
    private String password;

    public UserRequest(final String user_id, final String name, final String intro,
        final String password) {
        this.user_id = user_id;
        this.name = name;
        this.intro = intro;
        this.password = password;
    }

    public User toEntity(String image_url) {
        return User.builder().user_id(this.user_id).name(this.name)
            .intro(Optional.ofNullable(this.intro).orElse(""))
            .user_image(image_url)
            .password(this.password).build();

    }
}
