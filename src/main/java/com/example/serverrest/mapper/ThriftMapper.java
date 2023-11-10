package com.example.serverrest.mapper;

import com.example.serverrest.dto.response.OrderResponseDto;
import com.example.serverrest.dto.response.ShoppingCartResponseDto;
import communication.OrderThrift;
import communication.ShoppingCartThrift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThriftMapper {
    @Mapping(target = "productResponseDtoList", source = "productsThrifts")
    @Mapping(target = "customerResponseDto", source = "customerThrift")
    OrderResponseDto mapToOrderDto(OrderThrift orderThrift);

    @Mapping(target = "productResponseDtoList", source = "productsThrifts")
    @Mapping(target = "customerResponseDto", source = "customerThrift")
    ShoppingCartResponseDto mapToShoppingCartDto(ShoppingCartThrift shoppingCartThrift);
}
