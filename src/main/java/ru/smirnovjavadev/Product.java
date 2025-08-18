package ru.smirnovjavadev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Модель продукта: имя + список фасовок (items).
 * Класс неизменяемый — список доступен только для чтения.
 */
public final class Product {
    private final String name;
    private final List<Item> items;

    public Product(String name, List<Item> items) {
        this.name = Objects.requireNonNull(name, "name must not be null").trim();
        if (this.name.isEmpty()) throw new IllegalArgumentException("name must not be empty");
        Objects.requireNonNull(items, "items must not be null");
        // defensive copy + unmodifiable
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }

    public String getName() { return name; }
    public List<Item> getItems() { return items; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return name.equals(product.name) && items.equals(product.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, items);
    }

    @Override
    public String toString() {
        return "Product{" + "name='" + name + '\'' + ", items=" + items + '}';
    }
}
