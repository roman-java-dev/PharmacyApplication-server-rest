package com.example.serverrest.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequestDto(
        @Size(min = 3, max = 20)
        String name,
        @Min(0)
        BigDecimal price,
        @NotNull
        @Min(1)
        int quantity,
        String description) {
}
