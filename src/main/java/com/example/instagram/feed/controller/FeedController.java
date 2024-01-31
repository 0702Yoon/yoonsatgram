package com.example.instagram.feed.controller;

import com.example.instagram.feed.controller.dto.FeedRequest;
import com.example.instagram.feed.controller.dto.FeedResponse;
import com.example.instagram.feed.service.FeedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "게시글 API", description = "게시글 API")
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "게시글 업로드")
    public String upload(
        @RequestParam("data") final String data, @RequestParam("images") final
    List<MultipartFile> images) throws JsonProcessingException {
        FeedRequest feedRequest = objectMapper.readValue(data, FeedRequest.class);
        feedService.upload(feedRequest, images);
        return "게시글 업로드 완료";
    }

    @GetMapping("/feed_id/{feed_id}")
    @Operation(summary = "게시글id 조회")
    public FeedResponse getFeed(@PathVariable(value = "feed_id") final int feed_id) {
        return feedService.getFeed(feed_id);
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping(value = "/{feed_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FeedResponse patchFeed(@PathVariable(value = "feed_id") final int feed_id,
        @RequestParam("data") final String data, @RequestParam("images") final
    List<MultipartFile> images) throws JsonProcessingException {
        FeedRequest feedRequest = objectMapper.readValue(data, FeedRequest.class);
        return feedService.patchFeed(feed_id, feedRequest, images);
    }


    @DeleteMapping("/{feed_id}")
    @Operation(summary = "게시글 삭제")
    public String deleteFeed(@PathVariable(value = "feed_id") final int feed_id) {
        feedService.deleteFeed(feed_id);
        return "지우기 성공";
    }

    @GetMapping("/user_id/{user_id}")
    @Operation(summary = "피드 조회")
    public List<FeedResponse> getFeedList(@PathVariable final String user_id) {
        return feedService.getFeedList(user_id);
    }
}
