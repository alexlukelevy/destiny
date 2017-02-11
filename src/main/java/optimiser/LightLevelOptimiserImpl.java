package optimiser;

import entities.*;

import java.util.*;

public class LightLevelOptimiserImpl implements LightLevelOptimiser {


    @Override
    public OptimisedInventory optimise(Inventory inventory) {
        Map<String, Item> weapons = optimise(inventory.getWeapons());
        Map<String, Item> armour = optimise(inventory.getArmour());

        return new OptimisedInventory(weapons, armour);
    }

    // simply compute largest differential

    private Map<String, Item> optimise(List<Bucket> buckets) {
        List<BucketHighest> bh = new ArrayList<>();

        for (Bucket bucket : buckets) {
            // sort the weapons by light-level
            List<Item> sorted = new ArrayList<>(bucket.getItems());
            sorted.sort(Comparator.comparingInt(Item::getLightLevel).reversed());

            // if exotic, also find highest non-exotic
            Item highest = sorted.get(0);
            Item highestNonExotic = sorted.stream()
                    .filter(item -> item.getGrade() != ItemGrade.Exotic)
                    .findFirst()
                    .get();

            bh.add(new BucketHighest(bucket.getName(), highest, highestNonExotic));
        }

        return optimiseH(bh);
    }

    private Map<String, Item> optimiseH(List<BucketHighest> bh) {

        // highest without any exotics
        int max_light = bh.stream().mapToInt(t -> t.getHighestNonExotic().getLightLevel()).sum();
        String exoticBucketName = null;

        for (BucketHighest h : bh) {
            if (h.isExotic()) {
                int max = lightLevel(h.getName(), bh);
                if (max > max_light) {
                    max_light = max;
                    exoticBucketName = h.getName();
                }
            }
        }

        Map<String, Item> hottest = new HashMap<>();

        for (BucketHighest h: bh) {
           if (h.getName().equals(exoticBucketName)) {
               hottest.put(h.getName(), h.getHighest());
           } else {
               hottest.put(h.getName(), h.getHighestNonExotic());
           }
        }

        return hottest;
    }

    private int lightLevel(String exoticBucketName, List<BucketHighest> bh) {
        int sum = 0;
        for (BucketHighest b : bh) {
            if (b.getName().equals(exoticBucketName)) {
                sum += b.getHighest().getLightLevel();
            } else {
                sum += b.getHighestNonExotic().getLightLevel();
            }
        }
        return sum;
    }

    private class BucketHighest {
        private String name;
        private Item highest;
        private Item highestNonExotic;

        public BucketHighest(String name, Item highest, Item highestNonExotic) {

            this.name = name;
            this.highest = highest;
            this.highestNonExotic = highestNonExotic;
        }

        public boolean isExotic() {
            return getHighest().getGrade() == ItemGrade.Exotic;
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

}