package ru.smirnovjavadev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Категория продуктов (имя + список продуктов).
 * Неизменяемая модель.
 */
public final class Category {
    private final String name;
    private final List<Product> products;

    public Category(String name, List<Product> products) {
        this.name = Objects.requireNonNull(name, "name must not be null").trim();
        if (this.name.isEmpty()) throw new IllegalArgumentException("name must not be empty");
        Objects.requireNonNull(products, "products must not be null");
        this.products = Collections.unmodifiableList(new ArrayList<>(products));
    }

    public String getName() { return name; }
    public List<Product> getProducts() { return products; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name) && products.equals(category.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, products);
    }

    @Override
    public String toString() {
        return "Category{" + "name='" + name + '\'' + ", products=" + products + '}';
    }
}
