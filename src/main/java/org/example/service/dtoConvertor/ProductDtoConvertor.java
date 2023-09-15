package org.example.service.dtoConvertor;

import org.example.dto.ProductDto;
import org.example.entities.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductDtoConvertor<ENTITY, DTO> implements Converter<Product, ProductDto> {

    @Override
    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getCurrencyCode(),
                product.getLimitDB(),
                product.getStatus());
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getCurrencyCode(),
                productDto.getLimitDB(),
                productDto.getStatus());
    }
}
