package uk.co.alexlevy.entities;

public enum CharacterClass {
    Titan(0),
    Hunter(1),
    Warlock(2);

    public int id;

    CharacterClass(int id) {
        this.id = id;
    }

    public static CharacterClass fromId(int id) {
        for (CharacterClass characterClass : CharacterClass.values()) {
            if (characterClass.id == id) {
                return characterClass;
            }
        }
        throw new IllegalArgumentException("Class Id " + id + " does not exist");
    }
}
