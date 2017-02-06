package entities;


public enum WeaponBucketIdentifiers {
    BUCKET_PRIMARY_WEAPON,
    BUCKET_SPECIAL_WEAPON,
    BUCKET_HEAVY_WEAPON;

    public static boolean contains(String value) {
        for(WeaponBucketIdentifiers identifier : WeaponBucketIdentifiers.values()) {
            if (identifier.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
