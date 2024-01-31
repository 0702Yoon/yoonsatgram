package com.example.instagram.comment2.repository;

import com.example.instagram.comment.entity.Comment;
import com.example.instagram.comment2.entity.Comment2;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Comment2Repository extends JpaRepository<Comment2, Integer> {

    @Query("SELECT c2 FROM Comment2 c2 WHERE c2.comment = :comment")
    List<Comment2> findAllByComment_id(Comment comment);
}
