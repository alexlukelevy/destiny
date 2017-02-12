package uk.co.alexlevy.entities;

import java.util.List;

public class Inventory {

    private List<Bucket> weapons;
    private List<Bucket> armour;

    public Inventory(List<Bucket> weapons, List<Bucket> armour) {
        this.weapons = weapons;
        this.armour = armour;
    }

    public List<Bucket> getWeapons() {
        return weapons;
    }

    public List<Bucket> getArmour() {
        return armour;
    }
}
