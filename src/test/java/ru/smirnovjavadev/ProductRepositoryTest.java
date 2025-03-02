package ru.smirnovjavadev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class ProductRepositoryTest {
    private ProductRepository productRepository;

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        productRepository = new ProductRepository();
    }

    @Test
    void productGetTest() {
        System.out.println("Test 1: ");
        String label = productRepository.getProducts()
               .get("Интерьерные краски Aura")
               .get("Nord")
               .getProductMap()
               .get(17113);
        Assertions.assertEquals("0,9л", label);
    }

    @Test
    void productGetTest_is_Null() {
        System.out.println("Test 2: ");
        String label = productRepository.getProducts()
                .get("Интерьерные краски Aura")
                .get("Nord")
                .getProductMap()
                .get(9999);
        Assertions.assertNull(label);
    }

}
