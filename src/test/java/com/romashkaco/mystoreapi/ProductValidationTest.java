package com.romashkaco.mystoreapi;

import com.romashkaco.mystoreapi.model.Product;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductValidationTest {

    private final Validator validator;

    public ProductValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidProduct() {
        Product product = new Product(1L, "Product", "Description", 1.0, true);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNameIsNull() {
        Product product = new Product(1L, null, "Description", 1.0, true);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
        assertEquals("Name is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testPriceBelowZero() {
        Product product = new Product(1L, "Product", "Description", -1.0, true);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
        assertEquals("Price cannot be less than 0", violations.iterator().next().getMessage());
    }

    @Test
    public void testNameTooLong() {
        Product product = new Product(1L, "A".repeat(256), "Description", 10.0, true);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
        assertEquals("Name cannot be longer than 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void testNameTooLongAndPriceBelowZero() {
        Product product = new Product(1L, "A".repeat(256), "Description", -1.0, true);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(2, violations.size());
        boolean nameErrorFound = false;
        boolean priceErrorFound = false;

        // Проходим по каждому нарушению и проверяем его сообщение
        for (ConstraintViolation<Product> violation : violations) {
            String message = violation.getMessage();
            if (message.equals("Name cannot be longer than 255 characters")) nameErrorFound = true;
            if (message.equals("Price cannot be less than 0")) priceErrorFound = true;
        }
        assertTrue(nameErrorFound);
        assertTrue(priceErrorFound);
    }
}
