package org.example.controller;

import org.example.dto.ProductDto;
import org.example.entities.Manager;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.exceptions.InvalidStatusException;
import org.example.service.ProductService;
import org.example.service.dtoConvertor.ProductDtoConvertor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService<Product> productService;

    private final ProductDtoConvertor<Product, ProductDto> productDtoConvertor;

    public ProductController(ProductService<Product> productService, ProductDtoConvertor<Product, ProductDto> productDtoConvertor) {
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
    public ProductDto getById(@PathVariable(name = "id") long id) throws InvalidStatusException {
        Product product = productService.getById(id);
        Manager manager = product.getManager();
        if (Status.INACTIVE == manager.getStatus()) {
            throw new InvalidStatusException(String.format("Manager with id %d is inactive: ", manager.getId()));
        } else if (Status.INACTIVE == product.getStatus()) {
            throw new InvalidStatusException(String.format("Product with id %d is inactive: ", product.getId()));
        }

        return productDtoConvertor.toDto(productService.getById(id));
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/update/status/{id}")
    public ProductDto updateStatus(
            @PathVariable long id,
            @RequestBody ProductDto productDto) {

        return productDtoConvertor.toDto(productService.updateStatus(id, productDto.getStatus()));
    }
}
