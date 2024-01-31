package com.example.instagram.feed.entity;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.image.entity.Image;
import com.example.instagram.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feed_id;
    private String feed_text;
    private LocalDateTime date;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();


    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();


    @Builder
    public Feed(int feed_id, String feed_text, User user, List<Image> images) {
        this.feed_id = feed_id;
        this.feed_text = feed_text;
        this.user = user;
        this.images = images;
    }

    public List<Comment> getComment() {
        return comments;
    }

    public Feed() {

    }


    public void patchFeed(String feedText) {
        this.feed_text = feedText;
        this.date = LocalDateTime.now();
    }

}
