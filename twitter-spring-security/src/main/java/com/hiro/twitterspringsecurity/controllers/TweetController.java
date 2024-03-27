package com.hiro.twitterspringsecurity.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hiro.twitterspringsecurity.dtos.CreateTweetDto;
import com.hiro.twitterspringsecurity.entities.Role;
import com.hiro.twitterspringsecurity.entities.Tweet;
import com.hiro.twitterspringsecurity.repositories.TweetRepository;
import com.hiro.twitterspringsecurity.repositories.UserRepository;

@RestController
public class TweetController {
    
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("null")
    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }

    @SuppressWarnings("null")
    @GetMapping("/tweets")
    public ResponseEntity<List<Tweet>> listTweets(JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        List<Tweet> tweets = new ArrayList<>();

        List<Tweet> tweetsDb = tweetRepository.findTweetByUser_id(UUID.fromString(token.getName()));

        var isAdmin = user.get().getRoles()
                .stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        for (Tweet tweet : tweetsDb) {
            if(isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
                tweets.add(tweet);
                System.out.println(tweet);
            }
        }

        return ResponseEntity.ok().body(tweets);
    }

    @SuppressWarnings("null")
    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles()
                .stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if(isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            tweetRepository.deleteById(tweetId);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().build();
    }
}