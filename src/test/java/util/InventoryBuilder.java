package util;

import entities.Bucket;
import entities.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryBuilder {

    private List<Bucket> weapons;
    private List<Bucket> armour;

    public InventoryBuilder() {
        weapons = new ArrayList<>();
        armour = new ArrayList<>();
    }

    public InventoryBuilder withWeapons(Bucket weaponBucket) {
        weapons.add(weaponBucket);
        return this;
    }

    public InventoryBuilder withArmour(Bucket armourBucket) {
        armour.add(armourBucket);
        return this;
    }

    public Inventory build() {
        return new Inventory(weapons, armour);
    }
}
