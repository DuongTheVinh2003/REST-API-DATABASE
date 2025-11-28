package com.example.restapidata.dto;

public record AuthRequest (
        String username,
        String password
) { }
