package uk.co.alexlevy.entities;

public class Item {

    // represents no item
    public static Item None = new Item("None", 0, ItemGrade.Common);

    private String name;
    private int lightLevel;
    private ItemGrade grade;

    public Item(String name, int lightLevel, ItemGrade grade) {
        this.name = name;
        this.lightLevel = lightLevel;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public ItemGrade getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (lightLevel != item.lightLevel) return false;
        if (!name.equals(item.name)) return false;
        return grade == item.grade;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + lightLevel;
        result = 31 * result + grade.hashCode();
        return result;
    }
}