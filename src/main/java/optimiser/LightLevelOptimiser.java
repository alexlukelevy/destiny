package optimiser;

import entities.Inventory;

import java.util.HashMap;

public interface LightLevelOptimiser {

    HashMap<String, String> optimise(Inventory inventory);

}
