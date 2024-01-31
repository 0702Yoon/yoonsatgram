package com.example.instagram.user.controller;

import com.example.instagram.user.controller.dto.FollowRequest;
import com.example.instagram.user.controller.dto.FollowResponse;
import com.example.instagram.user.controller.dto.UserRequest;
import com.example.instagram.user.controller.dto.UserResponse;
import com.example.instagram.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "유저 API", description = "유저 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원 가입")
    @Parameter(name = "UserRequest", description = "아이디,이름,소개글,비번")
    public String join(@RequestParam("UserRequest") final String userRequest,
        @RequestParam("image") final
        MultipartFile image) throws JsonProcessingException {
        userService.join(objectMapper.readValue(userRequest, UserRequest.class), image);
        return "회원가입 성공";
    }

    @GetMapping("/{user_id}")
    @Operation(summary = "유저 조회")
    @Parameter(name = "user_id", description = "아이디")
    public UserResponse findUser(@PathVariable(value = "user_id") final String user_id) {
        return userService.findById(user_id);
    }

    @PatchMapping(value = "/modify/all/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 정보 수정")
    @Parameter(name = "userRequest", description = "이름, 소개글, 비번")
    public UserResponse patchUserAll(@PathVariable(value = "user_id") final String user_id,
        @RequestParam("userRequest") final String userRequest,
        @RequestParam("image") final MultipartFile image) throws JsonProcessingException {
        return userService.patchUser(user_id,
            objectMapper.readValue(userRequest, UserRequest.class), image);
    }

    @PatchMapping(value = "/modify/intro/{user_id}")
    @Operation(summary = "유저 소개글 수정")
    public UserResponse patchUserIntro(@PathVariable(value = "user_id") final String user_id,
        @RequestBody String intro) {
        return userService.patchUserIntro(user_id, intro);
    }

    @PatchMapping(value = "/modify/image/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "유저 프로필 사진 수정")
    public UserResponse modifyUserImage(@PathVariable(value = "user_id") final String user_id,
        @RequestParam("image") final MultipartFile image) {
        return userService.modifyUserImage(user_id, image);
    }

    @Operation(summary = "유저 탈퇴")
    @Parameter(name = "userRequest", description = "아이디")
    @DeleteMapping("/delete")
    public String delete(@RequestBody final UserRequest userRequest) {
        userService.delete(userRequest.getUser_id());
        return "유저 정보 지우기 성공";
    }

    @Operation(summary = "팔로우 걸기")
    @PostMapping("/follow")
    public FollowResponse followUser(@RequestBody final FollowRequest followRequest) {
        return userService.follow(followRequest);
    }

    @GetMapping("/follow/{user_id}")
    @Operation(summary = "팔로우 목록 가져오기")
    public FollowResponse getFollowList(@PathVariable(value = "user_id") final String user_id) {
        return userService.getFollowList(user_id);
    }

    @DeleteMapping("/follow/{user_id}")
    @Operation(summary = "팔로우 취소")
    public FollowResponse deleteFollow(@PathVariable final String user_id,
        @RequestBody final FollowRequest followRequest) {
        return userService.deleteFollow(user_id, followRequest.getFollowed_id());
    }
}
