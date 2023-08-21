package org.example.controller;

import org.example.dto.ProductDto;
import org.example.service.ProductService;
import org.example.service.dtoConvertor.ProductDtoConvertor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    private final ProductDtoConvertor productDtoConvertor;

    public ProductController(ProductService productService, ProductDtoConvertor productDtoConvertor) {
        this.productService = productService;
        this.productDtoConvertor = productDtoConvertor;
    }

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.getAll()
                .stream()
                .map(productDtoConvertor::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable(name = "id") long id) {
        return productDtoConvertor.toDto(productService.getById(id));
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto){
        return productDtoConvertor.toDto(productService.save(productDtoConvertor.toEntity(productDto)));
    }
}
