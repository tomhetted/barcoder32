package ru.smirnovjavadev;

public class Item {
    private int id;
    private String volume;

    public Item(int id, String volume) {
        this.id = id;
        this.volume = volume;
    }

    public int getId() { return id; }
    public String getVolume() { return volume; }
}

