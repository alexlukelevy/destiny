package entities;

import java.util.List;

public class Bucket {
    private String name;
    private List<Item> items;

    public Bucket(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }
}

