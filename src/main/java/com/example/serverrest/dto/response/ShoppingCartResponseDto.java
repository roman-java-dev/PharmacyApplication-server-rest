package com.example.serverrest.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartResponseDto {
        private Long id;
        private List<ProductResponseDto> productResponseDtoList;
        private CustomerResponseDto customerResponseDto;
}
