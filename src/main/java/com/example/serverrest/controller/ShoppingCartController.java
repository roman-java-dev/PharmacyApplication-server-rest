package com.example.serverrest.controller;

import com.example.serverrest.dto.response.ShoppingCartResponseDto;
import com.example.serverrest.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingCarts")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService cartService;

    @GetMapping("/add")
    public void addProductToShoppingCart(@RequestParam Long productId,
                                         @RequestParam Long customerId) {
        cartService.addProductToShoppingCart(productId, customerId);
    }

    @GetMapping("/delete")
    public void deleteProductFromShoppingCart(@RequestParam Long productId,
                                              @RequestParam Long customerId) {
        cartService.deleteProductFromShoppingCart(productId, customerId);
    }

    @GetMapping("/clear")
    public void clear(@RequestParam Long customerId) {
        cartService.clear(customerId);
    }
    @GetMapping
    public ShoppingCartResponseDto findByCustomer(@RequestParam Long customerId) {
        return cartService.findByCustomer(customerId);
    }
}
