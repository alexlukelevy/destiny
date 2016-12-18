package optimiser;

import entities.Bucket;

import java.util.HashMap;
import java.util.List;

public interface LightLevelOptimiser {

    HashMap<String, String> optimise(List<Bucket> buckets);

}
