package com.hiro.twitterspringsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiro.twitterspringsecurity.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}