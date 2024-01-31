package com.example.instagram.comment.controller;

import com.example.instagram.comment.controller.dto.CommentRequest;
import com.example.instagram.comment.controller.dto.CommentResponse;
import com.example.instagram.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "댓글 API", description = "유저 API")
@RequestMapping("/{feed_id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    @Operation(summary = "댓글 등록")
    public String createComment(@RequestBody final CommentRequest commentRequest,
        @PathVariable(value = "feed_id") final int feed_id) {
        commentService.createComment(commentRequest, feed_id);
        return "댓글 추가 완료";
    }

    @GetMapping("/{comment_id}")
    @Operation(summary = "댓글 조회")
    public CommentResponse getComment(@PathVariable(value = "feed_id") final int feed_id,
        @PathVariable(value = "comment_id") final int comment_id) {
        return commentService.getComment(feed_id, comment_id);
    }

    @PatchMapping("/{comment_id}")
    @Operation(summary = "댓글 수정")
    public CommentResponse patchComment(@PathVariable(value = "feed_id") final int feed_id,
        @PathVariable(value = "comment_id") final int comment_id,
        @RequestBody final CommentRequest commentRequest) {
        return commentService.patchCommet(feed_id, comment_id, commentRequest);
    }

    @DeleteMapping("/{comment_id}")
    @Operation(summary = "댓글 삭제")
    public String patchComment(@PathVariable(value = "feed_id") final int feed_id,
        @PathVariable(value = "comment_id") final int comment_id) {
        commentService.delete(feed_id, comment_id);
        return "지우기 완료";
    }
}
