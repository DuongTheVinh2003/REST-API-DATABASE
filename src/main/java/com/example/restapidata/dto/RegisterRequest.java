package com.example.restapidata.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) { }
