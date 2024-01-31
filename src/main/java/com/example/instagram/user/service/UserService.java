package com.example.instagram.user.service;

import com.example.instagram.exception.ValueExistsException;
import com.example.instagram.s3Service.S3Service;
import com.example.instagram.user.controller.dto.FollowRequest;
import com.example.instagram.user.controller.dto.FollowResponse;
import com.example.instagram.user.controller.dto.UserRequest;
import com.example.instagram.user.controller.dto.UserResponse;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repositroy.FollowRepository;
import com.example.instagram.user.repositroy.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final S3Service s3Service;

    @Transactional
    public void join(UserRequest userRequest, MultipartFile image) {
        if (userRepository.findById(userRequest.getUser_id()).isPresent()) {
            throw new ValueExistsException("중복되는 아이디입니다.");
        }
        String image_url = s3Service.uploadFile(userRequest.getUser_id(), image);
        userRepository.save(userRequest.toEntity(image_url));
    }

    @Transactional(readOnly = true)
    public UserResponse findById(String user_id) {
        User user = getUser(user_id);
        return UserResponse.of(user);
    }

    @Transactional
    public UserResponse patchUser(String userId,
        @NotNull UserRequest userRequest, MultipartFile image) {
        User user = getUser(userId);
        String imageUrl = user.getUser_image();
        if (!user.getUser_image().equals(user.getUser_id() + image.getName())) {
            s3Service.deleteObject(user.getUser_image());
            imageUrl = s3Service.uploadFile(user.getUser_id(), image);
        }

        user.patchUser(userId, userRequest.getName(), imageUrl,
            userRequest.getPassword());
        return findById(userId);
    }


    @Transactional
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public FollowResponse follow(FollowRequest followRequest) {
        User follower = getUser(followRequest.getUser_id());
        User followered = getUser(followRequest.getFollowed_id());
        followRepository.save(followRequest.toEntity(follower, followered));

        return FollowResponse.of(follower);
    }

    @Transactional(readOnly = true)
    public FollowResponse getFollowList(String userId) {
        User user = getUser(userId);
        return FollowResponse.of(user);
    }

    @Transactional
    public FollowResponse deleteFollow(String userId, String followedId) {
        User user = getUser(userId);
        User followed = userRepository.findById(followedId).get();
        followRepository.deleteFollowed(user, followed);
        return getFollowList(userId);
    }

    @Transactional
    public UserResponse patchUserIntro(String userId, String intro) {
        User user = getUser(userId);
        user.modifyIntro(intro);
        return UserResponse.of(user);
    }

    @Transactional
    public UserResponse modifyUserImage(String user_id, MultipartFile image) {
        User user = getUser(user_id);
        s3Service.deleteObject(user.getUser_image());
        String imageUrl = s3Service.uploadFile(user.getUser_id(), image);
        user.modifyImage(imageUrl);
        return UserResponse.of(user);
    }


    @Transactional(readOnly = true)
    public User getUser(String user_id) {
        return userRepository.findById(user_id)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 아이디입니다."));
    }


}
