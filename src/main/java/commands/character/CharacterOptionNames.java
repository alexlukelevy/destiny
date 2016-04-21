package commands.character;

public enum CharacterOptionNames {

    Username("u", "username"),
    ClassType("c", "class");

    private String shortOpt;
    private String longOpt;

    CharacterOptionNames(String shortOpt, String longOpt)
    {
        this.shortOpt = shortOpt;
        this.longOpt = longOpt;
    }

    public String getShortOpt() {
        return shortOpt;
    }

    public String getLongOpt() {
        return longOpt;
    }
}