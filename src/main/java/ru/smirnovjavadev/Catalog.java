package ru.smirnovjavadev;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Корневой контейнер каталога — содержит список производителей.
 */
public class Catalog {
    private final List<Producer> producers;

    public Catalog(List<Producer> producers) {
        this.producers = Collections.unmodifiableList(
                Objects.requireNonNull(producers, "producers must not be null"));
    }

    public List<Producer> getProducers() {
        return producers;
    }

    @Override
    public String toString() {
        return "Catalog{producers=" + producers.size() + "}";
    }
}
