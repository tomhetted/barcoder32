package ru.smirnovjavadev;

import java.util.Objects;

public class Item {
    private final int id;
    private final String volume;

    public Item(int id, String volume) {
        this.id = id;
        this.volume = Objects.requireNonNull(volume, "volume must not be null");
    }

    public int getId() { return id; }
    public String getVolume() { return volume; }

    @Override
    public String toString() {
        return "Item{id=" + id + ", volume='" + volume + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
