package com.romashkaco.mystoreapi.service;

import com.romashkaco.mystoreapi.model.Product;
import com.romashkaco.mystoreapi.repository.ProductRepository;
import com.romashkaco.mystoreapi.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final SupplyService supplyService;
    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    @Autowired
    public InventoryService(SupplyService supplyService, SaleRepository saleRepository, ProductRepository productRepository) {
        this.supplyService = supplyService;
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public int getAvailableQuantity(Product product) {
        int totalSupplies = supplyService.sumQuantityByProductId(product.getId());
        int totalSales = saleRepository.sumQuantityByProductId(product.getId());

        return totalSupplies - totalSales;
    }

    public void updateProductAvailability(Product product, int availableQuantity) {
        boolean isAvailable = availableQuantity > 0;
        product.setAvailable(isAvailable);
        System.out.println(isAvailable);
        productRepository.save(product);
    }

    public double calculateSaleCost(Product product, int quantity) {
        return product.getPrice() * quantity;
    }
}