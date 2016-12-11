package service;

import java.util.List;

public class Bucket {
    public String name;
    public List<Item> items;

    public Bucket(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }
}

