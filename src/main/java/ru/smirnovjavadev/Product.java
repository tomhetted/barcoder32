package ru.smirnovjavadev;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Product {

    private Map<Integer, String> productMap;

    public Product(Map<Integer, String> productMap) {
        this.productMap = productMap;
    }

    public Map<Integer, String> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<Integer, String> productMap) {
        this.productMap = productMap;
    }
}