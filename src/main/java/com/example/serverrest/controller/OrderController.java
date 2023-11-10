package com.example.serverrest.controller;

import com.example.serverrest.dto.response.OrderResponseDto;
import com.example.serverrest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/complete")
    public OrderResponseDto completeOrder(@RequestParam Long customerId) {
        return orderService.completeOrder(customerId);
    }

    @GetMapping
    public List<OrderResponseDto> getOrdersHistory(@RequestParam Long customerId) {
        return orderService.getOrdersHistory(customerId);
    }
}
