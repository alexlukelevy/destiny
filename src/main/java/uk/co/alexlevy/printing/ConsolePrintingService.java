package uk.co.alexlevy.printing;

import uk.co.alexlevy.entities.DestinyCharacter;
import uk.co.alexlevy.entities.Item;
import uk.co.alexlevy.entities.OptimisedInventory;

import java.util.Map;

public class ConsolePrintingService implements PrintingService {

    @Override
    public void print(String output) {
        System.out.println(output);
    }

    @Override
    public void print(DestinyCharacter character) {
        print("Character: " + character.getCharacterClass() + " - " + character.getLevel());
        print("");
    }

    @Override
    public void print(OptimisedInventory inventory) {
        print("Weapons:");
        print(inventory.getWeapons());
        print("");

        print("Armour:");
        print(inventory.getArmour());
        print("");
    }

    private void print(Map<String, Item> items) {
        items.forEach((bucketName, item) -> print(bucketName + ": " + item.getName()));
    }
}