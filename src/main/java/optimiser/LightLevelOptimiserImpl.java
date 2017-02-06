package optimiser;

import entities.Bucket;
import entities.Inventory;

import java.util.HashMap;

public class LightLevelOptimiserImpl implements LightLevelOptimiser {


    @Override
    public HashMap<String, String> optimise(Inventory inventory) {
        HashMap<String, String> results = new HashMap<>();

        // TODO: actually implement
        for (Bucket bucket : inventory.getWeapons()) {
            results.put(bucket.getName(), bucket.getItems().get(0).getName());
        }

        for (Bucket bucket : inventory.getArmour()) {
            results.put(bucket.getName(), bucket.getItems().get(0).getName());
        }

        return results;
    }
}