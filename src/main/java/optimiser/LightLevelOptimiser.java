package optimiser;

import entities.Inventory;
import entities.Item;
import entities.OptimisedInventory;

import java.util.HashMap;
import java.util.Map;

public interface LightLevelOptimiser {

    OptimisedInventory optimise(Inventory inventory);

}
