package uk.co.alexlevy.cli;

public class Environment {

    public String readPassword(String prompt) {
        return new String(System.console().readPassword(prompt));
    }

}