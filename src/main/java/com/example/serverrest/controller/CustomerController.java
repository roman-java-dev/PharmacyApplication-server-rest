package com.example.serverrest.controller;

import com.example.serverrest.dto.request.CustomerRequestDto;
import com.example.serverrest.dto.response.CustomerResponseDto;
import com.example.serverrest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public CustomerResponseDto add(@Valid @RequestBody CustomerRequestDto dto) {
        return service.add(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@PathVariable Long id,
                                      @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return service.update(id, customerRequestDto);

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
