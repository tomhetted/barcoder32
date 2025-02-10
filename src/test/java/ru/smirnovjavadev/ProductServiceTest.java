package ru.smirnovjavadev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        productService = new ProductService();
    }

    @Test
    void productGetTest() {
        System.out.println("Test 1: ");
       String id = productService.getProducts()
               .get("Интерьерные краски Aura")
               .get("Nord")
               .getProductMap()
               .get(17113);
       Assertions.assertEquals("0,9л", id);
    }


}
