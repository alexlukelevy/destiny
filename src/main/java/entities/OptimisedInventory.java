package entities;

import java.util.Map;

public class OptimisedInventory {
    private Map<String, Item> weapons;
    private Map<String, Item> armour;

    public OptimisedInventory(Map<String, Item> weapons, Map<String, Item> armour) {
        this.weapons = weapons;
        this.armour = armour;
    }

    public Map<String, Item> getWeapons() {
        return weapons;
    }

    public Map<String, Item> getArmour() {
        return armour;
    }
}
