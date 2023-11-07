package com.example.serverrest.dto.response;

import java.math.BigDecimal;

public record ProductResponseDto (
         Long id,
         String name,
         BigDecimal price,
         int quantity,
         String description) {
}
