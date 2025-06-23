package ru.smirnovjavadev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class ProductRepositoryTest {

    private Catalog catalog;

    @BeforeEach
    void setUp() {
        catalog = ProductRepository.getCatalog();
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
        // Проверим наличие категорий в нужном поставщике
        Optional<Producer> eskaroProducer = findProducerByName("Eskaro");
        assertTrue(eskaroProducer.isPresent());

        List<Category> categories = eskaroProducer.get().getCategories();

        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Интерьерные краски Aura")));
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Интерьерные краски Eskaro")));
    }

    @Test
    void shouldFindProductInCategory() {
        Optional<Category> categoryOpt = findCategory("Eskaro", "Интерьерные краски Aura");
        assertTrue(categoryOpt.isPresent());

        Optional<Product> productOpt = categoryOpt.get().getProducts().stream()
                .filter(p -> p.getName().equals("Nord"))
                .findFirst();

        assertTrue(productOpt.isPresent());
        assertEquals(3, productOpt.get().getItems().size());
    }

    // ==== Вспомогательные методы ====

    private Optional<String> findItemVolumeById(int id) {
        return catalog.getProducers().stream()
                .flatMap(p -> p.getCategories().stream())
                .flatMap(c -> c.getProducts().stream())
                .flatMap(p -> p.getItems().stream())
                .filter(i -> i.getId() == id)
                .map(Item::getVolume)
                .findFirst();
    }

    private Optional<Producer> findProducerByName(String name) {
        return catalog.getProducers().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }

    private Optional<Category> findCategory(String producerName, String categoryName) {
        return findProducerByName(producerName)
                .flatMap(p -> p.getCategories().stream()
                        .filter(c -> c.getName().equals(categoryName))
                        .findFirst());
    }
}
