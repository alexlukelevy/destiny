package commands.character;

import com.fasterxml.jackson.databind.JsonNode;
import commands.DestinyCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import service.DestinyService;
import service.PrintingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterCommand extends DestinyCommand {

    public CharacterCommand(Options options, DestinyService destinyService, PrintingService printingService) {
        super(options, destinyService, printingService);
    }

    @Override
    public void run(String[] args) throws IOException {
        // TODO: Pass commandLine as an argument instead of creating a new one every time
        CommandLine commandLine = getCommandLine(args);

        String instruction = getInstruction(commandLine);

        // is this statement the best way to do this?
        switch (instruction) {
            case "getSummary":  getCharacterSummary(args);
                break;
            case "getWeapons":  getCharacterWeapons(args);
                break;
            case "getArmor":  getCharacterArmor(args);
                break;
            default: System.out.println("The instruction supplied was not valid for characters");
                break;
        }


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

    public void getCharacterWeapons(String[] args) throws IOException {
        CommandLine commandLine = getCommandLine(args);

        String username = getUsername(commandLine);
        int classTypeId = getClassTypeId(commandLine);

        long membershipId = destinyService.getMembershipId(2, username);
        long characterId = destinyService.getCharacterId(2, membershipId, classTypeId);
        List<JsonNode> allCharacterItems = destinyService.getCharacterInventoryItems(2, membershipId, characterId);

        ArrayList<String> weapons = destinyService.getCharacterWeapons(2, membershipId, allCharacterItems, characterId);

        printingService.print(weapons);
    }

    public void getCharacterArmor(String[] args) throws IOException {
        CommandLine commandLine = getCommandLine(args);

        String username = getUsername(commandLine);
        int classTypeId = getClassTypeId(commandLine);

        long membershipId = destinyService.getMembershipId(2, username);
        long characterId = destinyService.getCharacterId(2, membershipId, classTypeId);
        List<JsonNode> allCharacterItems = destinyService.getCharacterInventoryItems(2, membershipId, characterId);

        ArrayList<String> armor = destinyService.getCharacterArmor(2, membershipId, allCharacterItems, characterId);

        printingService.print(armor);
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

    // Why only getting short option here, won't that mean long option is not valid?
    private String getInstruction(CommandLine commandLine) {
        String opt = CharacterOptionNames.Instruction.getShortOpt();
        if(commandLine.hasOption(opt)) {
            return commandLine.getOptionValue(opt);
        }
        throw new IllegalArgumentException("The " + opt + " option value was not supplied");
    }
}