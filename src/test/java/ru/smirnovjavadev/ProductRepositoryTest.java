package ru.smirnovjavadev;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    private List<Category> categories;

    @BeforeEach
    void setUp() {
        categories = ProductRepository.getProducts();
    }

    @Test
    void shouldFindExistingItem() {
        // Act
        Optional<String> volume = findItemVolumeById(17113);

        // Assert
        assertTrue(volume.isPresent());
        assertEquals("0,9л", volume.get());
    }

    @Test
    void shouldReturnEmptyForNonExistingItem() {
        // Act
        Optional<String> volume = findItemVolumeById(9999);

        // Assert
        assertFalse(volume.isPresent());
    }

    @Test
    void shouldContainExpectedCategories() {
        // Assert
        assertTrue(categories.stream()
                .anyMatch(c -> c.getName().equals("Интерьерные краски Aura")));

        assertTrue(categories.stream()
                .anyMatch(c -> c.getName().equals("Интерьерные краски Eskaro")));
    }

    @Test
    void shouldFindProductInCategory() {
        // Act
        Optional<Product> nordProduct = categories.stream()
                .filter(c -> c.getName().equals("Интерьерные краски Aura"))
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p.getName().equals("Nord"))
                .findFirst();

        // Assert
        assertTrue(nordProduct.isPresent());
        assertEquals(3, nordProduct.get().getItems().size());
    }

    private Optional<String> findItemVolumeById(int id) {
        return categories.stream()
                .flatMap(c -> c.getProducts().stream())
                .flatMap(p -> p.getItems().stream())
                .filter(i -> i.getId() == id)
                .map(Item::getVolume)
                .findFirst();
    }
}
