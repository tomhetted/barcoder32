package ru.smirnovjavadev;

import java.util.Objects;

/**
 * Небольшая неизменяемая модель фасовки продукта (id + volume).
 */
public final class Item {
    private final int id;
    private final String volume;

    public Item(int id, String volume) {
        if (id < 0) throw new IllegalArgumentException("id must be non-negative");
        this.id = id;
        this.volume = Objects.requireNonNull(volume, "volume must not be null").trim();
        if (this.volume.isEmpty()) throw new IllegalArgumentException("volume must not be empty");
    }

    public int getId() { return id; }
    public String getVolume() { return volume; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id == item.id && volume.equals(item.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volume);
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", volume='" + volume + '\'' + '}';
    }
}
