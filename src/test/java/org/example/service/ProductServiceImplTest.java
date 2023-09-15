package org.example.service;

import org.example.entities.Manager;
import org.example.entities.Product;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.enums.Currency.USD;
import static org.example.enums.Status.ACTIVE;
import static org.example.enums.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ManagerServiceImpl managerService;

    @Test
    void getAll() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1L, "creditOneForUSD", USD, 20, ACTIVE));
        products.add(new Product(2L, "creditTwoForUSD", USD, 12, ACTIVE));

        when(productRepository.findAll()).thenReturn(products);
        List<Product> savedAll = productService.getAll();

        assertEquals(products, savedAll);
    }

    @Test
    void getByIdWhenIdExists() {
        Product product = Product.builder()
                .id(1L)
                .name("creditOneForUSD")
                .currencyCode(USD)
                .limitDB(20)
                .status(ACTIVE).build();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertEquals(product, productService.getById(1L));
    }

    @Test
    void getByIdWhenNotExists() {
        Product product = Product.builder()
                .name("creditOneForUSD")
                .currencyCode(USD)
                .limitDB(20)
                .status(ACTIVE).build();

        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> productService.getById(product.getId()));
    }

    @Test
    void create() {

        Manager manager = Manager.builder()
                .id(1L)
                .status(ACTIVE)
                .firstName("Ihor")
                .lastName("Hnatchenko").build();

        when(managerService.getById(manager.getId())).thenReturn(manager);

        Product product = Product.builder()
                .name("creditOneForUSD")
                .currencyCode(USD)
                .limitDB(20)
                .status(ACTIVE)
                .manager(manager).build();

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        Product saved = productService.save(product);
        assertEquals(product, saved);
    }

    @Test
    void updateStatus() {

        Product product = Product.builder()
                .name("creditOneForUSD")
                .currencyCode(USD)
                .limitDB(20)
                .status(ACTIVE).build();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Product withUpdateStatus = productService.updateStatus(product.getId(), INACTIVE);

        assertEquals(INACTIVE, withUpdateStatus.getStatus());
    }
}