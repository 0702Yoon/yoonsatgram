package com.example.instagram.feed.controller.dto;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.feed.entity.Feed;
import com.example.instagram.image.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedResponse {

    @Schema(example = "게시글 Id")
    private int feed_id;
    private String feed_text;
    private LocalDateTime date;
    private List<Image> images;
    private String user_id;
    private List<Comment> commentList;

    @Builder
    private FeedResponse(final int feed_id, final String feed_text, final LocalDateTime date,
        final List<Image> images, final String user_id, final List<Comment> commentList) {
        this.feed_id = feed_id;
        this.feed_text = feed_text;
        this.date = date;
        this.images = images;
        this.commentList = commentList;
        this.user_id = user_id;
    }


    public static FeedResponse of(Feed feed) {
        return FeedResponse.builder().feed_text(feed.getFeed_text()).date(feed.getDate())
            .images(feed.getImages()).user_id(feed.getUser().getUser_id())
            .feed_id(feed.getFeed_id()).commentList(feed.getComments())
            .build();
    }
}
