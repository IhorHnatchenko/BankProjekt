package org.example.service;

import org.example.enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService<P> {

    List<P> getAll();

    P getById(long productId);

    P save(P product);

    P updateStatus(long productId, Status status);
}
