package com.example.instagram.comment.repository;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.feed.entity.Feed;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.feed = :feed AND c.comment_id = :comment_id")
    Optional<Comment> findByFeed_idAndComment_id(Feed feed, int comment_id);


    @Modifying
    @Transactional
    @Query("DELETE From Comment Where feed = :feed And comment_id = :commentId")
    void deleteByFeedIdAndCommentId(Feed feed, int commentId);
}
