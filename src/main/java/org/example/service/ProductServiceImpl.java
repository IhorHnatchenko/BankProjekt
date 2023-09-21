package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entities.Manager;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService<Product> {

    private final ProductRepository productRepository;

    private final ManagerService<Manager> managerService;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect product id " + productId));
    }

    @Override
    public Product save(Product product) {
        Manager manager = managerService.getById(product.getManager().getId());
        product.setManager(manager);

        return productRepository.save(product);
    }

    @Override
    public Product updateStatus(long productId, Status status) {
        Product product = getById(productId);
        product.setStatus(status);

        return productRepository.save(product);
    }
}
