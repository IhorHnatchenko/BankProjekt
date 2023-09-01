package org.example.service;

import org.example.entities.Product;
import org.example.enums.Status;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(long productId) {
        return productRepository.getReferenceById(productId);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateStatus(long productId, Status status) {
        Product product = productRepository.getReferenceById(productId);

        product.setStatus(status);
        return productRepository.save(product);
    }
}
