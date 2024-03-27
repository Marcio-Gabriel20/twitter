package com.hiro.twitterspringsecurity.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hiro.twitterspringsecurity.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query(value = """
            SELECT * FROM tb_tweet WHERE user_id=:idUser
            """, nativeQuery = true)
    List<Tweet> findTweetByUser_id(UUID idUser);
}