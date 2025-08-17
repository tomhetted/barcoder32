package ru.smirnovjavadev;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Product {
    private final String name;
    private final List<Item> items;

    public Product(String name, List<Item> items) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.items = Collections.unmodifiableList(
                Objects.requireNonNull(items, "items must not be null"));
    }

    public String getName() { return name; }
    public List<Item> getItems() { return items; }

    @Override
    public String toString() {
        return "Product{name='" + name + "', items=" + items.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
