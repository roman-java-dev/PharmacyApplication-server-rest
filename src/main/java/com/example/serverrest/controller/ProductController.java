package com.example.serverrest.controller;

import com.example.serverrest.dto.request.ProductRequestDto;
import com.example.serverrest.dto.response.ProductResponseDto;
import com.example.serverrest.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ProductResponseDto add(@Valid @RequestBody ProductRequestDto dto) {
        return service.add(dto);
    }

    @GetMapping("/")
    public ProductResponseDto getByName(@RequestParam String name) {
        return service.getByName(name);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @Valid @RequestBody ProductRequestDto productRequestDto) {
        return service.update(id, productRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
