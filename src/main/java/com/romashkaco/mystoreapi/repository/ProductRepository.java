package com.romashkaco.mystoreapi.repository;

import com.romashkaco.mystoreapi.model.Product;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public void save(Product product) {
        products.add(product);
    }

    public void deleteById(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    public Optional<Product> update(Product updatedProduct) {
        return findById(updatedProduct.getId())
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setAvailable(updatedProduct.isAvailable());
                    return existingProduct;
                });
    }
}
