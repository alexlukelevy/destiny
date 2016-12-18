package cli;

public class Environment {

    public String getVariable(String name) {
        return System.getenv(name);
    }

    public String readLine(String prompt) {
        return System.console().readLine(prompt);
    }

}