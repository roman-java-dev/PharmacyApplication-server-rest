package com.example.serverrest.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
        private Long id;
        private List<ProductResponseDto> productResponseDtoList;
        private LocalDateTime orderDate;
        private CustomerResponseDto customerResponseDto;
}
