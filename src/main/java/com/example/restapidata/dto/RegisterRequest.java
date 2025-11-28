package com.example.REST_API_DATA.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) { }
