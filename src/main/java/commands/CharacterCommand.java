package commands;

import service.DestinyService;
import service.PrintingService;

import java.io.IOException;

public class CharacterCommand extends DestinyCommand {

    public CharacterCommand(DestinyService destinyService, PrintingService printingService) {
        super(destinyService, printingService);
    }

    @Override
    public void run(String username) {
        // TODO: enable choice of membership
        try {
            long membershipId = destinyService.getMembershipId(2, username);
            String summary = destinyService.getCharacterSummary(2, membershipId);
            printingService.print(summary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}