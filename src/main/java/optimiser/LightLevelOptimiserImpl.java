package optimiser;

import entities.Bucket;

import java.util.HashMap;
import java.util.List;

public class LightLevelOptimiserImpl implements LightLevelOptimiser {


    @Override
    public HashMap<String, String> optimise(List<Bucket> buckets) {
        HashMap<String, String> results = new HashMap<>();

        // TODO: actually implement
        for (Bucket bucket : buckets) {
            results.put(bucket.name, bucket.items.get(0).getName());
        }

        return results;
    }
}