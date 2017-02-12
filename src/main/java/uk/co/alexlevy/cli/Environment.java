package uk.co.alexlevy.cli;

public class Environment {

    public String readLine(String prompt) {
        return System.console().readLine(prompt);
    }

}