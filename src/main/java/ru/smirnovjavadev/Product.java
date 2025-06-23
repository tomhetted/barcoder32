package ru.smirnovjavadev;


import java.util.List;

public class Product {
    private String name;
    private List<Item> items;

    public Product(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() { return name; }
    public List<Item> getItems() { return items; }
}