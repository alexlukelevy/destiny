package io;

import commands.DestinyCommand;

import java.util.EnumMap;

public class CommandLineInterface {

    private EnumMap<CommandType, DestinyCommand> commands;

    public CommandLineInterface(EnumMap<CommandType, DestinyCommand> commands) {
        this.commands = commands;
    }

    public void run(String[] args) {
        CommandType commandType = CommandType.valueOf(args[0]);
        String username = args[1];

        if (commands.containsKey(commandType)) {
            DestinyCommand destinyCommand = commands.get(commandType);
            destinyCommand.run(username);
        } else {
            throw new IllegalArgumentException(args[0] + " is not a supported command");
        }
    }
}