package ru.smirnovjavadev;

import java.util.List;

public class Category {
    private String name;
    private List<Product> products;

    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public List<Product> getProducts() { return products; }
}