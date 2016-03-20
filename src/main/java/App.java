import commands.CharacterCommand;
import commands.DestinyCommand;
import io.CommandLineInterface;
import io.CommandType;
import service.ApacheDestinyService;
import service.ConsolePrintingService;
import service.DestinyService;
import service.PrintingService;

import java.io.IOException;
import java.util.EnumMap;

public class App {

    public static void main(String[] args) throws IOException {
        DestinyService destinyService = new ApacheDestinyService(
                "http://www.bungie.net/Platform/Destiny",
                System.getenv("DESTINY_API_KEY")
        );
        PrintingService printingService = new ConsolePrintingService();

        EnumMap<CommandType, DestinyCommand> commands = new EnumMap<>(CommandType.class);
        commands.put(CommandType.character, new CharacterCommand(destinyService, printingService));

        new CommandLineInterface(commands).run(args);
    }
}