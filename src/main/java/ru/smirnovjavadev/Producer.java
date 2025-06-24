package ru.smirnovjavadev;

import java.util.List;

public class Producer {
    private String name;
    private List<Category> categories;

    public Producer(String name, List<Category> categories) {
        this.name = name;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
