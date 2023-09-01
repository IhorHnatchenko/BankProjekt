package org.example.service;

import org.example.entities.Product;
import org.example.enums.Status;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProductService {

    List<Product> getAll();

    Product getById(long productId);

    Product save(Product product);

    Product updateStatus(long productId, Status status);
}
