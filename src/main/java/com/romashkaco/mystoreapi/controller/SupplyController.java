package com.romashkaco.mystoreapi.controller;

import com.romashkaco.mystoreapi.filter.SupplyRequestDTO;
import com.romashkaco.mystoreapi.model.Supply;
import com.romashkaco.mystoreapi.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/supply")
public class SupplyController {

    private final SupplyService supplyService;

    @Autowired
    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupplies() {
        return ResponseEntity.ok(supplyService.getAllSupplies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable Long id) {
        return ResponseEntity.ok(supplyService.getSupplyById(id));
    }

    @PostMapping
    public ResponseEntity<Supply> createSupply(@Valid @RequestBody SupplyRequestDTO supplyRequestDTO) {
        Supply createdSupply = supplyService.createSupply(supplyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supply> updateSupply(@PathVariable Long id, @Valid @RequestBody SupplyRequestDTO supplyRequestDTO) {
        Supply updatedSupply = supplyService.updateSupply(id, supplyRequestDTO);
        return ResponseEntity.ok(updatedSupply);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }
}
