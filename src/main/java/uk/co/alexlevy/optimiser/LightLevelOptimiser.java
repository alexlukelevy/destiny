package uk.co.alexlevy.optimiser;

import uk.co.alexlevy.entities.Inventory;
import uk.co.alexlevy.entities.OptimisedInventory;

public interface LightLevelOptimiser {

    OptimisedInventory optimise(Inventory inventory);

}
