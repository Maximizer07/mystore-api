package com.romashkaco.mystoreapi.service;

import com.romashkaco.mystoreapi.exception.InvalidSaleRequestException;
import com.romashkaco.mystoreapi.exception.SaleNotFoundException;
import com.romashkaco.mystoreapi.filter.SaleRequestDTO;
import com.romashkaco.mystoreapi.model.Product;
import com.romashkaco.mystoreapi.model.Sale;
import com.romashkaco.mystoreapi.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleService(SaleRepository saleRepository, ProductService productService, InventoryService inventoryService, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.modelMapper = modelMapper;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException(id));
    }

    public Sale createSale(SaleRequestDTO saleRequestDTO) {
        Sale sale = modelMapper.map(saleRequestDTO, Sale.class);

        Long productId = saleRequestDTO.getProduct_id();
        Product product = productService.getProductById(productId);
        sale.setProduct(product);

        int availableQuantity = inventoryService.getAvailableQuantity(product);

        if (sale.getQuantity() > availableQuantity) {
            throw new InvalidSaleRequestException("It is impossible to sell more goods than are in stock.");
        }

        inventoryService.updateProductAvailability(product, availableQuantity - sale.getQuantity());

        double cost = inventoryService.calculateSaleCost(product, sale.getQuantity());
        sale.setCost(cost);

        return saleRepository.save(sale);
    }

    public Sale updateSale(Long id, SaleRequestDTO saleRequestDTO) {
        Sale existingSale = getSaleById(id);

        modelMapper.map(saleRequestDTO, existingSale);

        Long productId = saleRequestDTO.getProduct_id();
        Product product = productService.getProductById(productId);
        existingSale.setProduct(product);
        return saleRepository.save(existingSale);
    }

    public void deleteSale(Long id) {
        Sale existingSale = getSaleById(id);
        saleRepository.delete(existingSale);

    }
}