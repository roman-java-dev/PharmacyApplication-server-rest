package com.example.serverrest.dto.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record CustomerRequestDto (
        @Size(min = 2, max = 20)
        String firstName,
        @Size(min = 2, max = 20)
        String lastName,
        @Pattern(regexp = PATTERN)
        String phoneNumber,
        String email,
        String password
) {
    private final static String PATTERN = "^380[0-9]{9}$";
}
