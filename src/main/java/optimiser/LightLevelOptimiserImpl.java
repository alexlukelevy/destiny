package optimiser;

import entities.*;

import java.util.*;
import java.util.stream.Collectors;

public class LightLevelOptimiserImpl implements LightLevelOptimiser {

    @Override
    public OptimisedInventory optimise(Inventory inventory) {
        Map<String, Item> weapons = optimise(inventory.getWeapons());
        Map<String, Item> armour = optimise(inventory.getArmour());

        return new OptimisedInventory(weapons, armour);
    }

    private Map<String, Item> optimise(List<Bucket> buckets) {
        List<OptimisedBucket> optimised = buildOptimisedBuckets(buckets);

        int maxDiff = 0;
        OptimisedBucket exoticBucket = null;

        // select the Exotic that will give overall highest light level
        for (OptimisedBucket bucket : optimised) {
            int diff = bucket.exoticVsNonExoticLightLevelDiff();
            if (diff > maxDiff) {
                maxDiff = diff;
                exoticBucket = bucket;
            }
        }

        Map<String, Item> results = new HashMap<>();

        for (OptimisedBucket bucket : optimised) {
            results.put(bucket.getName(), optimalItem(bucket, exoticBucket));
        }

        return results;
    }

    private List<OptimisedBucket> buildOptimisedBuckets(List<Bucket> buckets) {
        return buckets.stream().map(this::buildOptimisedBucket).collect(Collectors.toList());
    }

    private OptimisedBucket buildOptimisedBucket(Bucket bucket) {

        // user might not have any items in a bucket
        if(bucket.getItems().size() == 0) {
            return new OptimisedBucket(bucket.getName(), Item.None, Item.None);
        }

        // sort the weapons by light-level desc
        List<Item> sorted = new ArrayList<>(bucket.getItems());
        sorted.sort(Comparator.comparingInt(Item::getLightLevel).reversed());

        // find highest and highest non-exotic (could be the same item)
        Item highest = sorted.get(0);
        Item highestNonExotic = sorted.stream()
                .filter(item -> item.getGrade() != ItemGrade.Exotic)
                .findFirst()
                .get();

        return new OptimisedBucket(bucket.getName(), highest, highestNonExotic);
    }

    private Item optimalItem(OptimisedBucket bucket, OptimisedBucket exoticBucket) {
        return exoticBucket == bucket ? bucket.getHighest() : bucket.getHighestNonExotic();
    }

}