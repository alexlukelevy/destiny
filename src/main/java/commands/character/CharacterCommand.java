package commands.character;

import commands.DestinyCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import service.DestinyService;
import service.PrintingService;

import java.io.IOException;

public class CharacterCommand extends DestinyCommand {

    public CharacterCommand(Options options, DestinyService destinyService, PrintingService printingService) {
        super(options, destinyService, printingService);
    }

    @Override
    public void run(String[] args) throws IOException {
        // defaults to summary for now
        getCharacterSummary(args);
    }

    public void getCharacterSummary(String[] args) throws IOException {
        // TODO: enable choice of membership
        CommandLine commandLine = getCommandLine(args);

        String username = getUsername(commandLine);
        int classTypeId = getClassTypeId(commandLine);

        long membershipId = destinyService.getMembershipId(2, username);
        long characterId = destinyService.getCharacterId(2, membershipId, classTypeId);
        String summary = destinyService.getCharacterSummary(2, membershipId, characterId);

        printingService.print(summary);
    }

    private String getUsername(CommandLine commandLine) {
        String opt = CharacterOptionNames.Username.getShortOpt();
        if(commandLine.hasOption(opt)) {
            return commandLine.getOptionValue(opt);
        }
        throw new IllegalArgumentException("The " + opt + " option value was not supplied");
    }

    private int getClassTypeId(CommandLine commandLine) {
        String opt = CharacterOptionNames.ClassType.getShortOpt();
        if(commandLine.hasOption(opt)) {
            String classType = commandLine.getOptionValue(opt);
            return ClassType.getId(classType);
        }
        throw new IllegalArgumentException("The " + opt + " option value was not supplied");
    }
}