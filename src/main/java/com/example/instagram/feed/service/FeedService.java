package com.example.instagram.feed.service;

import com.example.instagram.feed.controller.dto.FeedRequest;
import com.example.instagram.feed.controller.dto.FeedResponse;
import com.example.instagram.feed.entity.Feed;
import com.example.instagram.feed.repository.FeedRepository;
import com.example.instagram.image.entity.Image;
import com.example.instagram.image.repository.ImageRepository;
import com.example.instagram.s3Service.S3Service;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repositroy.FollowRepository;
import com.example.instagram.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserService userService;
    private final ImageRepository imageRepository;

    private final FollowRepository followRepository;


    private final S3Service s3Service;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void upload(FeedRequest feedRequest, List<MultipartFile> images) {
        User user = userService.getUser(feedRequest.getUser_id());
        Feed feed = feedRepository.save(feedRequest.toEntity(user));

        for (MultipartFile image : images) {
            String image_urls = s3Service.uploadFile(Integer.toString(feed.getFeed_id()), image);
            imageRepository.save(Image.builder().image_url(image_urls).feed(feed).build());
        }

    }

    @Transactional(readOnly = true)
    public FeedResponse getFeed(int feedId) {
        Feed feed = getFeedEntity(feedId);
        return FeedResponse.of(feed);
    }

    @Transactional
    public FeedResponse patchFeed(int feedId, FeedRequest feedRequest, List<MultipartFile> images) {
        Feed feed = getFeedEntity(feedId);
        feed.patchFeed(feedRequest.getFeed_text());
        imageRepository.findAllByFeed_id(feed)
            .forEach(image -> s3Service.deleteObject(image.getImage_url()));
        imageRepository.deleteAllByFeed_id(feed);
        flushAndClearContext();

        images.stream()
            .map(image -> s3Service.uploadFile(Integer.toString(feedId), image))
            .map(image_name -> Image.builder().image_url(image_name).feed(feed).build())
            .forEach(imageRepository::save);
        flushAndClearContext();
        return getFeed(feedId);
    }

    @Transactional
    public void deleteFeed(int feedId) {
        feedRepository.deleteById(feedId);
    }

    @Transactional(readOnly = true)
    public List<FeedResponse>
    getFeedList(String userId) {
        User user = userService.getUser(userId);
        List<User> followList = followRepository.findByUser(user);
        List<Feed> feedList = feedRepository.findByUsers(followList);
        return feedList.stream().map(FeedResponse::of).toList();
    }

    private void flushAndClearContext() {
        entityManager.flush();
        entityManager.clear();
    }

    @Transactional(readOnly = true)
    public Feed getFeedEntity(int feedId) {
        return feedRepository.findById(feedId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 게시글입니다."));
    }
}
