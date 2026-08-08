package com.example.movieapi.dto;

public record TokenPair(
        String accessToken,
        String refreshToken)
{}