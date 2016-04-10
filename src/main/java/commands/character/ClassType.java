package commands.character;

public enum ClassType {

    titan(0),
    hunter(1),
    warlock(2);

    private int id;

    ClassType(int id) {
        this.id = id;
    }

    public static int getId(String name) {
        // TODO: handle IllegalArgumentException
        return ClassType.valueOf(name.toLowerCase()).id;
    }

}