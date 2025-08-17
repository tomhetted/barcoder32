package ru.smirnovjavadev;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Producer {
    private final String name;
    private final List<Category> categories;

    public Producer(String name, List<Category> categories) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.categories = Collections.unmodifiableList(
                Objects.requireNonNull(categories, "categories must not be null"));
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Producer{name='" + name + "', categories=" + categories.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producer)) return false;
        Producer producer = (Producer) o;
        return name.equals(producer.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
