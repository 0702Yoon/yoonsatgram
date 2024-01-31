package com.example.instagram.user.entity;


import com.example.instagram.comment.entity.Comment;
import com.example.instagram.feed.entity.Feed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user_table")
public class User {

    @Id
    private String user_id;

    private String name;

    private String intro;

    private String password;

    private String user_image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE)
    private List<Follow> followers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "followed", cascade = CascadeType.REMOVE)
    private List<Follow> followedList = new ArrayList<>();


    @Builder
    public User(String user_id, String name, String intro, String user_image, String password) {
        this.user_id = user_id;
        this.name = name;
        this.intro = intro;
        this.user_image = user_image;
        this.password = password;
    }

    public User() {

    }

    public void patchUser(String userId, String name, String userImage, String password) {
        this.user_id = userId;
        this.name = name;
        this.user_image = userImage;
        this.password = password;
    }

    public void putUser(String userId, String name, String userImage, String password) {
        this.user_id = userId;
        this.name = name;
        this.user_image = userImage;
        this.password = password;
        System.out.println(this.user_id);
    }

    public void modifyIntro(String intro) {
        this.intro = intro;
    }

    public void modifyImage(String imageUrl) {
        this.user_image = imageUrl;
    }
}
