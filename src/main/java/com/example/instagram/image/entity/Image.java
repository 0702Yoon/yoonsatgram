package com.example.instagram.image.entity;

import com.example.instagram.feed.entity.Feed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Image {

    @Id
    private String image_url;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    @JsonIgnore
    private Feed feed;

    @Builder
    public Image(String image_url, Feed feed) {
        this.image_url = image_url;
        this.feed = feed;
    }

    public Image() {

    }

    public void updateImage(String newImageUrl) {
        this.image_url = newImageUrl;
    }
}
