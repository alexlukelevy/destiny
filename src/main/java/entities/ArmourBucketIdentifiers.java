package entities;


public enum ArmourBucketIdentifiers {
    BUCKET_GHOST,
    BUCKET_HEAD,
    BUCKET_ARMS,
    BUCKET_CHEST,
    BUCKET_LEGS,
    BUCKET_CLASS_ITEMS,
    BUCKET_ARTIFACT;

    public static boolean contains(String value) {
        for (ArmourBucketIdentifiers identifier : ArmourBucketIdentifiers.values()) {
            if (identifier.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
