package ru.smirnovjavadev;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Category {
    private final String name;
    private final List<Product> products;

    public Category(String name, List<Product> products) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.products = Collections.unmodifiableList(
                Objects.requireNonNull(products, "products must not be null"));
    }

    public String getName() { return name; }
    public List<Product> getProducts() { return products; }

    @Override
    public String toString() {
        return "Category{name='" + name + "', products=" + products.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
