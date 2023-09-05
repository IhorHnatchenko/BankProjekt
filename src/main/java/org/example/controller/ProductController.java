package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.ProductDto;
import org.example.entities.Manager;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.service.ProductService;
import org.example.service.dtoConvertor.ProductDtoConvertor;
import org.springframework.http.ResponseEntity;
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
        Product product = productService.getById(id);
        Manager manager = product.getManager();
        if (manager.getStatus().equals(Status.INACTIVE) || product.getStatus().equals(Status.INACTIVE)) {
            return null;
        }

        return productDtoConvertor.toDto(productService.getById(id));
    }

    @PostMapping
    public Product save(@RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<Product> updateStatus(
            @PathVariable long id,
            @RequestBody Product product){
        try {
            Product productWithUpdateStatus = productService.updateStatus(id, product.getStatus());
            return ResponseEntity.ok(productWithUpdateStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

/*    {
        "id": 2,
        "status": 1,
        "firstName": "Yo",
        "lastName": "Boku"
    }*/
/*{
    "name": "credit1",
        "status": 1,
        "currencyCode": "USD",
        "interestRate": 67,
        "limitDB": 12
}*/

}
