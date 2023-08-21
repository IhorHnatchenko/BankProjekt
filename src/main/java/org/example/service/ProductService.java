package org.example.service;

import org.example.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getAll();

    Product getById(long productId);

    Product save(Product product);
}
