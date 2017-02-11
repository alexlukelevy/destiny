package printing;

import entities.DestinyCharacter;
import entities.Item;
import entities.OptimisedInventory;

import java.util.Map;

public class ConsolePrintingService implements PrintingService {

    @Override
    public void print(String output) {
        System.out.println(output);
    }

    @Override
    public void print(DestinyCharacter character) {
        print("Character: " + character.getCharacterClass() + " - " + character.getLevel());
    }

    @Override
    public void print(OptimisedInventory inventory) {
        print("Weapons:");
        print(inventory.getWeapons());

        print("Armour:");
        print(inventory.getArmour());
    }

    private void print(Map<String, Item> items) {
        items.forEach((bucketName, item) -> {
            print(bucketName + ": " + item);
        });
    }
}