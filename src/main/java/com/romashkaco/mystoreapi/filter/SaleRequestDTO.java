package com.romashkaco.mystoreapi.filter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleRequestDTO {
    @NotNull(message = "Document name is required")
    private String documentName;

    @NotNull(message = "Product ID is required")
    private Long product_id;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
}