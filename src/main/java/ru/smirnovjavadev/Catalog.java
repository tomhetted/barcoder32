package ru.smirnovjavadev;

import java.util.List;

public class Catalog {
    private List<Producer> producers;

    public Catalog(List<Producer> producers) {
        this.producers = producers;
    }

    public List<Producer> getProducers() {
        return producers;
    }
}
