package optimiser;

import entities.Inventory;
import entities.OptimisedInventory;

public interface LightLevelOptimiser {

    OptimisedInventory optimise(Inventory inventory);

}
