package com.hiro.twitterspringsecurity.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiro.twitterspringsecurity.dtos.CreateTweetDto;
import com.hiro.twitterspringsecurity.entities.Tweet;
import com.hiro.twitterspringsecurity.repositories.TweetRepository;
import com.hiro.twitterspringsecurity.repositories.UserRepository;

@RestController
public class TweetController {
    
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }
}