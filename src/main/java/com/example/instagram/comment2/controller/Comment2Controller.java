package com.example.instagram.comment2.controller;

import com.example.instagram.comment2.controller.dto.Comment2Request;
import com.example.instagram.comment2.controller.dto.Comment2Response;
import com.example.instagram.comment2.service.Comment2Service;
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
@Tag(name = "대댓글 API", description = "유저 API")
@RequestMapping("/{comment_id}/comments2")
@RequiredArgsConstructor
public class Comment2Controller {

    private final Comment2Service comment2Service;

    @PostMapping()
    @Operation(summary = "대댓글 등록")
    public String createComment2(@RequestBody final Comment2Request comment2Request,
        @PathVariable(value = "comment_id") final int comment_id) {
        comment2Service.createComment2(comment_id, comment2Request);
        return "대댓글 추가";
    }

    @GetMapping("/{comment2_id}")
    @Operation(summary = "대댓글 조회")
    public Comment2Response getComment2(@PathVariable(value = "comment_id") int comment_id,
        @PathVariable(value = "comment2_id") int comment2_id) {
        return comment2Service.getComment2(comment2_id);
    }

    @PatchMapping("/{comment2_id}")
    @Operation(summary = "대댓글 수정")
    public Comment2Response patchComment2(
        @PathVariable(value = "comment2_id") final int comment2_id,
        @RequestBody final Comment2Request comment2Request) {
        return comment2Service.patchComment2(comment2_id, comment2Request);
    }

    @DeleteMapping("/{comment2_id}")
    @Operation(summary = "대댓글 삭제")
    public String deleteComment2(@PathVariable(value = "comment2_id") final int comment2_id) {
        comment2Service.deleteComment2(comment2_id);
        return "지우기 완료";
    }
}
