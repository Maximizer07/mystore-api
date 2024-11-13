package com.romashkaco.mystoreapi.filter;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ProductFilterDTO {


    @Nullable
    @Size(max = 255, message = "Name filter cannot be longer than 255 characters")
    private String name;


    @Nullable
    @Min(value = 0, message = "Minimum price cannot be less than 0")
    private Double minPrice;

    @Nullable
    @Min(value = 0, message = "Maximum price cannot be less than 0")
    private Double maxPrice;

    @Nullable
    @Pattern(regexp = "true|false", message = "Available must be 'true' or 'false'")
    private String available;

    @Nullable
    @Pattern(regexp = "name|price", message = "SortBy must be 'name' or 'price'")
    private String sortBy;

    @Min(value = 1, message = "Limit must be at least 1")
    private int limit=100;

    @AssertTrue(message = "Min price cannot be greater than max price")
    public boolean isPriceRangeValid() {
        if (minPrice != null && maxPrice != null) {
            return minPrice <= maxPrice;
        }
        return true;
    }
}