package com.example.instagram.comment2.service;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment.repository.CommentRepository;
import com.example.instagram.comment2.controller.dto.Comment2Request;
import com.example.instagram.comment2.controller.dto.Comment2Response;
import com.example.instagram.comment2.entity.Comment2;
import com.example.instagram.comment2.repository.Comment2Repository;
import com.example.instagram.feed.entity.Feed;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Comment2Service {

    private final Comment2Repository comment2Repository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment2(int comment_id, Comment2Request comment2Request) {
        User user = userRepository.findById(comment2Request.getUser_id()).get();
        Comment comment = commentRepository.findById(comment_id).get();
        comment2Repository.save(comment2Request.toEntity(user, comment));

    }

    @Transactional(readOnly = true)
    public Comment2Response getComment2(int comment2Id) {
        Comment2 comment2 = comment2Repository.findById(comment2Id).get();
        return Comment2Response.of(comment2);
    }

    @Transactional
    public Comment2Response patchComment2(int comment2Id, Comment2Request comment2Request) {
        Comment2 comment2 = comment2Repository.findById(comment2Id).get();
        comment2.patchComment2(comment2Request);
        return getComment2(comment2Id);
    }

    @Transactional
    public void deleteComment2(int comment2Id) {
        comment2Repository.deleteById(comment2Id);

    }
}
