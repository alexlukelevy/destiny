package uk.co.alexlevy.entities;

public class DestinyCharacter {

    private long id;
    private CharacterClass characterClass;
    private int level;

    public DestinyCharacter(long id, CharacterClass characterClass, int level) {
        this.id = id;
        this.characterClass = characterClass;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DestinyCharacter that = (DestinyCharacter) o;

        if (id != that.id) return false;
        if (level != that.level) return false;
        return characterClass == that.characterClass;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + characterClass.hashCode();
        result = 31 * result + level;
        return result;
    }
}
