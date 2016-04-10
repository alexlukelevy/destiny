import commands.CommandLineInterface;
import commands.CommandType;
import commands.DestinyCommand;
import commands.character.CharacterCommand;
import commands.character.CharacterOptions;
import org.apache.commons.cli.Options;
import service.ApacheDestinyService;
import service.ConsolePrintingService;
import service.DestinyService;
import service.PrintingService;

import java.util.EnumMap;

public class App {

    public static void main(String[] args) throws Exception {
        DestinyService destinyService = new ApacheDestinyService(
                "http://www.bungie.net/Platform/Destiny",
                System.getenv("DESTINY_API_KEY")
        );
        PrintingService printingService = new ConsolePrintingService();

        EnumMap<CommandType, DestinyCommand> commands = getCommands(destinyService, printingService);

        new CommandLineInterface(commands).run(args);
    }

    private static EnumMap<CommandType, DestinyCommand> getCommands(
            DestinyService destinyService,
            PrintingService printingService) {

        EnumMap<CommandType, DestinyCommand> commands = new EnumMap<>(CommandType.class);

        // character
        Options characterOptions = new CharacterOptions().getOptions();
        commands.put(CommandType.character, new CharacterCommand(characterOptions, destinyService, printingService));

        return commands;
    }
}