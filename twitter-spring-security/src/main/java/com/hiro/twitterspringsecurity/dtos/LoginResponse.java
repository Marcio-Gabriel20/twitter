package com.hiro.twitterspringsecurity.dtos;

public record LoginResponse(String accessToken, Long expiresIn) {   
}