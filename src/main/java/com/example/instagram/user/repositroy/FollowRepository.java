package com.example.instagram.user.repositroy;

import com.example.instagram.user.entity.Follow;
import com.example.instagram.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    @Modifying
    @Query("delete from Follow follow where follow .follower=:user and follow .followed=:followed")
    void deleteFollowed(User user, User followed);

    @Query("Select follow.followed FROM Follow follow where follow .follower = :user")
    List<User> findByUser(User user);
}
