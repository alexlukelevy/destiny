package io;

public enum Commands {

    Character;

    public static boolean contains(String value) {
        for(Commands c : Commands.values()) {
            String lower = c.toString().toLowerCase();
            if (lower.equals(value)) {
                return true;
            }
        }
        return false;
    }
}