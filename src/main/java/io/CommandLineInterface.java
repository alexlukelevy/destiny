package io;

public class CommandLineInterface {

    public void run(String[] args) {
        if(Commands.contains(args[0])) {
            // TODO: delegate accordingly
        } else {
            throw new IllegalArgumentException(args[0] + " is not a supported command");
        }
    }
}