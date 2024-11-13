package com.romashkaco.mystoreapi.service;

import com.romashkaco.mystoreapi.exception.SupplyNotFoundException;
import com.romashkaco.mystoreapi.filter.SupplyRequestDTO;
import com.romashkaco.mystoreapi.model.Product;
import com.romashkaco.mystoreapi.model.Supply;
import com.romashkaco.mystoreapi.repository.SupplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {
    private final SupplyRepository supplyRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplyService(SupplyRepository supplyRepository, ProductService productService, ModelMapper modelMapper) {
        this.supplyRepository = supplyRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Supply getSupplyById(Long id) {
        return supplyRepository.findById(id).orElseThrow(() -> new SupplyNotFoundException(id));
    }

    public Supply createSupply(SupplyRequestDTO supplyRequestDTO) {
        Supply supply = modelMapper.map(supplyRequestDTO, Supply.class);

        Long productId = supplyRequestDTO.getProduct_id();
        Product product = productService.getProductById(productId);
        supply.setProduct(product);

        return supplyRepository.save(supply);
    }

    public Supply updateSupply(Long id, SupplyRequestDTO supplyRequestDTO) {
        Supply existingSupply = getSupplyById(id);

        modelMapper.map(supplyRequestDTO, existingSupply);

        Long productId = supplyRequestDTO.getProduct_id();
        Product product = productService.getProductById(productId);
        existingSupply.setProduct(product);

        return supplyRepository.save(existingSupply);
    }

    public void deleteSupply(Long id) {
        Supply existingSupply = getSupplyById(id);
        supplyRepository.delete(existingSupply);
    }

    public int sumQuantityByProductId(Long productId) {
        return supplyRepository.findAllByProductId(productId)
                .stream()
                .mapToInt(Supply::getQuantity)
                .sum();
    }
}
