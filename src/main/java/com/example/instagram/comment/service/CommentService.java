package com.example.instagram.comment.service;

import com.example.instagram.comment.controller.dto.CommentRequest;
import com.example.instagram.comment.controller.dto.CommentResponse;
import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.feed.entity.Feed;
import com.example.instagram.feed.repository.FeedRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;


    @Transactional
    public void createComment(CommentRequest commentRequest, int feed_id) {
        User user = userRepository.findById(commentRequest.getUser_id()).get();
        Feed feed = feedRepository.findById(feed_id).get();
        commentRepository.save(commentRequest.toEntity(user, feed));
    }

    @Transactional
    public CommentResponse patchCommet(int feedId, int commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.patchComment(commentRequest);
        return getComment(feedId, commentId);
    }

    @Transactional(readOnly = true)
    public CommentResponse getComment(int feedId, int commentId) {
        Comment comment = commentRepository.findByFeed_idAndComment_id(
                feedRepository.findById(feedId).get(), commentId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 없습니다."));
        return CommentResponse.of(comment);
    }

    @Transactional
    public void delete(int feedId, int commentId) {
        Feed feed = feedRepository.findById(feedId).get();
        commentRepository.deleteByFeedIdAndCommentId(feed, commentId);
    }
}
