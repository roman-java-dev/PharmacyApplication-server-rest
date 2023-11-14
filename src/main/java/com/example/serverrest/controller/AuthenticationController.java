package com.example.serverrest.controller;

import com.example.serverrest.dto.request.CustomerLoginDto;
import com.example.serverrest.dto.request.CustomerRequestDto;
import com.example.serverrest.dto.response.CustomerResponseDto;
import com.example.serverrest.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public CustomerResponseDto register(@RequestBody @Valid CustomerRequestDto dto) {
        return service.register(dto);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody CustomerLoginDto dto) {
        return service.login(dto);
    }
}
