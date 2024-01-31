package com.example.instagram.feed.repository;

import com.example.instagram.feed.entity.Feed;
import com.example.instagram.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedRepository extends JpaRepository<Feed, Integer> {

    @Query("select f from Feed f where f.user in :users")
    List<Feed> findByUsers(@Param("users") List<User> users);
}
