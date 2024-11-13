package com.romashkaco.mystoreapi.service;

import com.romashkaco.mystoreapi.exception.ProductNotFoundException;
import com.romashkaco.mystoreapi.filter.ProductFilterDTO;
import com.romashkaco.mystoreapi.model.Product;
import com.romashkaco.mystoreapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(ProductFilterDTO filterDTO) {
        Specification<Product> specification = buildSpecification(filterDTO);
        Sort sort = buildSort(filterDTO);
        PageRequest pageRequest = PageRequest.of(0, filterDTO.getLimit(), sort);
        Page<Product> page = productRepository.findAll(specification, pageRequest);
        return page.getContent();
    }

    private Specification<Product> buildSpecification(ProductFilterDTO filterDTO) {
        return (root, query, builder) -> {
            Boolean isAvailable = null;
            if (filterDTO.getAvailable() != null) {
                isAvailable = Boolean.valueOf(filterDTO.getAvailable());
            }
            var predicate = builder.conjunction();

            if (filterDTO.getName() != null) {
                predicate = builder.and(predicate, builder.like(root.get("name"), "%" + filterDTO.getName() + "%"));
            }

            if (filterDTO.getMinPrice() != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("price"), filterDTO.getMinPrice()));
            }

            if (filterDTO.getMaxPrice() != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("price"), filterDTO.getMaxPrice()));
            }

            if (isAvailable != null) {
                predicate = builder.and(predicate, builder.equal(root.get("available"), isAvailable));
            }

            return predicate;
        };
    }

    private Sort buildSort(ProductFilterDTO filterDTO) {
        if (filterDTO.getSortBy() != null) {
            if ("price".equalsIgnoreCase(filterDTO.getSortBy())) {
                return Sort.by(Sort.Order.asc("price"));
            } else if ("name".equalsIgnoreCase(filterDTO.getSortBy())) {
                return Sort.by(Sort.Order.asc("name"));
            }
        }
        return Sort.unsorted();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
