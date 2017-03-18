package uk.co.alexlevy.optimiser;

import uk.co.alexlevy.entities.Item;

public class OptimisedBucket {
    private String name;
    private Item highest;
    private Item highestNonExotic;

    public OptimisedBucket(String name, Item highest, Item highestNonExotic) {

        this.name = name;
        this.highest = highest;
        this.highestNonExotic = highestNonExotic;
    }

    public int exoticVsNonExoticLightLevelDiff() {
        return getHighest().getLightLevel() - getHighestNonExotic().getLightLevel();
    }

    public String getName() {
        return name;
    }

    public Item getHighest() {
        return highest;
    }

    public Item getHighestNonExotic() {
        return highestNonExotic;
    }
}
