package com.romashkaco.mystoreapi.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;

    @NotNull(message = "Name is required")
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    private String name;

    @Size(max = 4096, message = "Description cannot be longer than 4096 characters")
    private String description = "";

    @Min(value = 0, message = "Price cannot be less than 0")
    private double price = 0.0;

    private boolean available = false;
}
