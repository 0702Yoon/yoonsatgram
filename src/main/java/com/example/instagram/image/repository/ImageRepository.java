package com.example.instagram.image.repository;

import com.example.instagram.feed.entity.Feed;
import com.example.instagram.image.entity.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, String> {

    @Modifying
    @Transactional
    @Query("delete from Image  Where feed = ?1")
    void deleteAllByFeed_id(Feed feed);

    @Query("SELECT image FROM Image image WHERE image.feed = :feed")
    List<Image> findAllByFeed_id(Feed feed);
}
