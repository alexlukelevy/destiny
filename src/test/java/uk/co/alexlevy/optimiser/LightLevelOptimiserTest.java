package uk.co.alexlevy.optimiser;

import org.junit.Before;
import org.junit.Test;
import uk.co.alexlevy.entities.*;
import uk.co.alexlevy.util.InventoryBuilder;

import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class LightLevelOptimiserTest {

    private LightLevelOptimiserImpl classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new LightLevelOptimiserImpl();
    }

    @Test
    public void shouldOptimiseForHighLightLevel() {
        // Given
        Inventory inventory = new InventoryBuilder()
                .withWeapons(primaryWeapons())
                .withWeapons(specialWeapons())
                .withWeapons(heavyWeapons())
                .withWeapons(ghosts())
                .withArmour(heads())
                .withArmour(arms())
                .withArmour(chests())
                .withArmour(legs())
                .withArmour(classItems())
                .withArmour(artifacts())
                .build();

        // When
        OptimisedInventory optimised = classUnderTest.optimise(inventory);

        // Then
        Map<String, Item> weapons = optimised.getWeapons();
        assertThat(weapons.get("PRIMARY_WEAPON"), equalTo(new Item("PrimaryWeapon2", 320, ItemGrade.Legendary)));
        assertThat(weapons.get("SPECIAL_WEAPON"), equalTo(new Item("SpecialWeapon1", 318, ItemGrade.Rare)));
        assertThat(weapons.get("HEAVY_WEAPON"), equalTo(new Item("HeavyWeapon2", 322, ItemGrade.Exotic)));
        assertThat(weapons.get("GHOST"), equalTo(new Item("Ghost2", 322, ItemGrade.Uncommon)));

        Map<String, Item> armour = optimised.getArmour();
        assertThat(armour.get("HEAD"), equalTo(new Item("Head1", 320, ItemGrade.Rare)));
        assertThat(armour.get("ARM"), equalTo(new Item("Arm2", 322, ItemGrade.Exotic)));
        assertThat(armour.get("CHEST"), equalTo(new Item("Chest1", 310, ItemGrade.Rare)));
        assertThat(armour.get("LEG"), equalTo(new Item("Leg1", 310, ItemGrade.Rare)));
        assertThat(armour.get("CLASS_ITEM"), equalTo(new Item("ClassItem1", 310, ItemGrade.Rare)));
        assertThat(armour.get("ARTIFACT"), equalTo(new Item("Artifact1", 300, ItemGrade.Common)));
    }

    @Test
    public void shouldOptimiseWithNoExotics() {
        // Given
        Bucket primaryWeapons = new Bucket("PRIMARY_WEAPON", asList(new Item("PrimaryWeapon", 310, ItemGrade.Rare)));
        Bucket head = new Bucket("HEAD", asList(new Item("Head", 320, ItemGrade.Common)));

        Inventory inventory = new InventoryBuilder()
                .withWeapons(primaryWeapons)
                .withArmour(head)
                .build();

        // When
        OptimisedInventory optimised = classUnderTest.optimise(inventory);

        // Then
        Map<String, Item> weapons = optimised.getWeapons();
        assertThat(weapons.get("PRIMARY_WEAPON"), equalTo(primaryWeapons.getItems().get(0)));

        Map<String, Item> armour = optimised.getArmour();
        assertThat(armour.get("HEAD"), equalTo(head.getItems().get(0)));
    }

    @Test
    public void shouldOptimiseWithEmptyBucket() {
        // Given
        Bucket primaryWeapons = new Bucket("PRIMARY_WEAPON", asList(new Item("PrimaryWeapon", 310, ItemGrade.Rare)));
        Bucket head = new Bucket("HEAD", asList());

        Inventory inventory = new InventoryBuilder()
                .withWeapons(primaryWeapons)
                .withArmour(head)
                .build();

        // When
        OptimisedInventory optimised = classUnderTest.optimise(inventory);

        // Then
        Map<String, Item> weapons = optimised.getWeapons();
        assertThat(weapons.get("PRIMARY_WEAPON"), equalTo(primaryWeapons.getItems().get(0)));

        Map<String, Item> armour = optimised.getArmour();
        assertThat(armour.get("HEAD"), equalTo(Item.None));
    }

    public Bucket primaryWeapons() {
        Item item1 = new Item("PrimaryWeapon1", 310, ItemGrade.Common);
        Item item2 = new Item("PrimaryWeapon2", 320, ItemGrade.Legendary);

        return new Bucket("PRIMARY_WEAPON", asList(item1, item2));
    }

    public Bucket specialWeapons() {
        Item item1 = new Item("SpecialWeapon1", 318, ItemGrade.Rare);
        Item item2 = new Item("SpecialWeapon2", 325, ItemGrade.Exotic);

        return new Bucket("SPECIAL_WEAPON", asList(item1, item2));
    }

    public Bucket heavyWeapons() {
        Item item1 = new Item("HeavyWeapon1", 310, ItemGrade.Rare);
        Item item2 = new Item("HeavyWeapon2", 322, ItemGrade.Exotic);

        return new Bucket("HEAVY_WEAPON", asList(item1, item2));
    }

    public Bucket ghosts() {
        Item item1 = new Item("Ghost1", 280, ItemGrade.Rare);
        Item item2 = new Item("Ghost2", 322, ItemGrade.Uncommon);

        return new Bucket("GHOST", asList(item1, item2));
    }

    public Bucket heads() {
        Item item1 = new Item("Head1", 320, ItemGrade.Rare);
        Item item2 = new Item("Head2", 330, ItemGrade.Exotic);

        return new Bucket("HEAD", asList(item1, item2));
    }

    public Bucket arms() {
        Item item1 = new Item("Arm1", 280, ItemGrade.Uncommon);
        Item item2 = new Item("Arm2", 322, ItemGrade.Exotic);

        return new Bucket("ARM", asList(item1, item2));
    }

    public Bucket chests() {
        Item item1 = new Item("Chest1", 310, ItemGrade.Rare);
        Item item2 = new Item("Chest2", 330, ItemGrade.Exotic);

        return new Bucket("CHEST", asList(item1, item2));
    }

    public Bucket legs() {
        Item item1 = new Item("Leg1", 310, ItemGrade.Rare);

        return new Bucket("LEG", asList(item1));
    }

    public Bucket classItems() {
        Item item1 = new Item("ClassItem1", 310, ItemGrade.Rare);
        Item item2 = new Item("ClassItem2", 304, ItemGrade.Legendary);

        return new Bucket("CLASS_ITEM", asList(item1, item2));
    }

    public Bucket artifacts() {
        Item item1 = new Item("Artifact1", 300, ItemGrade.Common);

        return new Bucket("ARTIFACT", asList(item1));
    }
}
