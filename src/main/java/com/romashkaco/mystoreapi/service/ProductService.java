package com.romashkaco.mystoreapi.service;

import com.romashkaco.mystoreapi.exception.ProductNotFoundException;
import com.romashkaco.mystoreapi.model.Product;
import com.romashkaco.mystoreapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private final AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        product.setId(idCounter.getAndIncrement());
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        updatedProduct.setId(id);
        productRepository.update(updatedProduct);
        return updatedProduct;
    }

    public void deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }
}
