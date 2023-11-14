package com.example.serverrest.config;

import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
public record ServiceProperties(
        String customer,
        String product,
        String order,
        String shoppingCart,
        String auth
){
}
