package com.example.serverrest.dto.response;

public record CustomerResponseDto(
         Long id,
         String firstName,
         String lastName,
         Long phoneNumber,
         String email,
         Integer bonus) {
}
