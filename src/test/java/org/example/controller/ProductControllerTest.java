package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ProductDto;
import org.example.entities.Manager;
import org.example.entities.Product;
import org.example.exceptions.BadArgumentsException;
import org.example.service.ProductService;
import org.example.service.dtoConvertor.ProductDtoConvertor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.enums.Currency.JPY;
import static org.example.enums.Status.INACTIVE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.example.enums.Status.ACTIVE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @MockBean
    private ProductService<Product> productService;

    @MockBean
    private ProductDtoConvertor convertor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {

        Product product = new Product(1L, "credit", JPY, 21, ACTIVE);

        when(productService.getAll()).thenReturn(List.of(product));

        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getCurrencyCode(),
                product.getLimitDB(),
                product.getStatus());

        when(convertor.toDto(product)).thenReturn(productDto);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(List.of(productDto))));
    }

    @Test
    void getByIdWhenIdIsExists() throws Exception {
        Manager manager = new Manager(1L, "Alex", "Alexeev", ACTIVE);

        Product product = new Product(1L, "credit", JPY, 21, ACTIVE, manager);

        when(productService.getById(product.getId())).thenReturn(product);

        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getCurrencyCode(),
                product.getLimitDB(),
                product.getStatus());

        when(convertor.toDto(product)).thenReturn(productDto);

        mockMvc.perform(get("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(productDto)));
    }

    @Test
    void getByIdWhenIdIsNotExists() throws Exception {
        Manager manager = new Manager(1L, "Alex", "Alexeev", ACTIVE);

        Product notExistsProduct = new Product(1L, "credit", JPY, 21, ACTIVE, manager);

        when(productService.getById(notExistsProduct.getId())).thenThrow(BadArgumentsException.class);

        mockMvc.perform(get("/products/" + notExistsProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException));
    }

    @Test
    void createProduct() throws Exception {
        Product product = new Product("credit", JPY, 21, ACTIVE);

        when(productService.save(product)).thenReturn(product);

        mockMvc.perform(post("/products/")
                        .content(asJsonString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("credit"))
                .andExpect(jsonPath("$.currencyCode").value("JPY"))
                .andExpect(jsonPath("$.limitDB").value("21"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void updateStatus() throws Exception {

        Product product = new Product(1L, "credit", JPY, 21, ACTIVE);

        ProductDto productDto = new ProductDto(1L, "credit", JPY, 21, ACTIVE);

        when(convertor.toEntity(productDto)).thenReturn(product);

        when(productService.updateStatus(product.getId(), INACTIVE)).thenReturn(product);

        when(convertor.toDto(product)).thenReturn(
                new ProductDto(1L, "credit", JPY, 21, ACTIVE));

        mockMvc.perform(put("/products/update/status/{id}", 1L)
                        .content(asJsonString(product))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}