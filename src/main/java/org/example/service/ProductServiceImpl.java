package org.example.service;

import org.example.entities.Manager;
import org.example.entities.Product;
import org.example.enums.Status;
import org.example.repository.ManagerRepository;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ManagerRepository managerRepository;

    public ProductServiceImpl(ProductRepository productRepository, ManagerRepository managerRepository) {
        this.productRepository = productRepository;
        this.managerRepository = managerRepository;
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
        if (product.getManager() != null && product.getManager().getId() != 0){
            Manager manager = managerRepository.findById(product.getManager().getId()).get();
            product.setManager(manager);
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public Product updateStatus(long productId, Status status) {
        Product product = productRepository.getReferenceById(productId);

        product.setStatus(status);
        return productRepository.save(product);
    }
}
