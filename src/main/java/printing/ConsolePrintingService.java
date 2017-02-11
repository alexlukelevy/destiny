package printing;

import entities.ArmourBucketIdentifiers;
import entities.OptimisedInventory;
import entities.WeaponBucketIdentifiers;

public class ConsolePrintingService implements PrintingService {

    @Override
    public void print(String output) {
        System.out.println(output);
    }

    @Override
    public void print(OptimisedInventory inventory) {
        print("Weapons:");
        for (WeaponBucketIdentifiers identifier : WeaponBucketIdentifiers.values()) {
            print(identifier.toString() + ": " + inventory.getWeapons());
        }

        print("Armour:");
        for (ArmourBucketIdentifiers identifier : ArmourBucketIdentifiers.values()) {
            print(identifier.toString() + ": " + inventory.getArmour());
        }
    }

}