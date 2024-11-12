package com.romashkaco.mystoreapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    @Column(name = "name", nullable = false, length = 255)
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    private String name;

    @Size(max = 4096, message = "Description cannot be longer than 4096 characters")
    @Column(name = "description", length = 4096)
    private String description = "";

    @Column(name = "price")
    @ColumnDefault("0")
    @Min(value = 0, message = "Price cannot be less than 0")
    private double price = 0.0;

    @Column(name = "available")
    @ColumnDefault("false")
    private boolean available = false;
}
