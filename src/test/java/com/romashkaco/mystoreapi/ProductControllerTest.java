package com.romashkaco.mystoreapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romashkaco.mystoreapi.controller.ProductController;
import com.romashkaco.mystoreapi.exception.ProductNotFoundException;
import com.romashkaco.mystoreapi.model.Product;
import com.romashkaco.mystoreapi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;
    private Product sampleProduct;

    @BeforeEach
    public void setup() {
        sampleProduct = new Product(1L, "Sample Product", "Sample Description", 100.0, true);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(sampleProduct));

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sample Product"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"));
    }

    @Test
    public void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(sampleProduct);

        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.description").value("Sample Description"));
    }

    @Test
    public void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(sampleProduct);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.description").value("Sample Description"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(sampleProduct);

        mockMvc.perform(put("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.description").value("Sample Description"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/product/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(2L)).thenThrow(new ProductNotFoundException(2L));

        mockMvc.perform(get("/api/product/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Product with ID " + 2L + " not found"));
    }
}
