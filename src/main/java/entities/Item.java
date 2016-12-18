package entities;

public class Item {
    public String name;
    public int lightLevel;
    public ItemGrade grade;

    public Item(String name, int lightLevel, ItemGrade grade) {
        this.name = name;
        this.lightLevel = lightLevel;
        this.grade = grade;
    }

}