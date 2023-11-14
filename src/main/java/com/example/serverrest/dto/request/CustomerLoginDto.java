package com.example.serverrest.dto.request;

public record CustomerLoginDto(
        String email,
        String password
) {
}
