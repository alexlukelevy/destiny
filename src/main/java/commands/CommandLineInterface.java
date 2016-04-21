package commands;

import java.util.EnumMap;

public class CommandLineInterface {

    private EnumMap<CommandType, DestinyCommand> commands;

    public CommandLineInterface(EnumMap<CommandType, DestinyCommand> commands) {
        this.commands = commands;
    }

    public void run(String[] args) throws Exception {
        CommandType commandType = CommandType.valueOf(args[0]);

        if (commands.containsKey(commandType)) {
            DestinyCommand destinyCommand = commands.get(commandType);
            destinyCommand.run(args);
        } else {
            throw new IllegalArgumentException(args[0] + " is not a supported command");
        }
    }
}