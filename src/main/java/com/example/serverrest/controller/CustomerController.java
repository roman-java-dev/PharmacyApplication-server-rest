package com.example.serverrest.controller;

import com.example.serverrest.dto.response.CustomerResponseDto;
import com.example.serverrest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/me")
    public CustomerResponseDto update(Authentication authentication,
                                      @Valid @RequestBody Map<String, Object> fields) {
        return service.update(authentication.getName(), fields);
    }

    @GetMapping
    public List<CustomerResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{phoneNumber}")
    public CustomerResponseDto getByPhoneNumber(@PathVariable Long phoneNumber) {
        return service.getByPhoneNumber(phoneNumber);
    }

    @GetMapping("/")
    public CustomerResponseDto getByFirstNameAndLastName(@RequestParam String firstName,
                                                         @RequestParam String lastname) {
        return service.getByFirstNameAndLastName(firstName, lastname);
    }
}
