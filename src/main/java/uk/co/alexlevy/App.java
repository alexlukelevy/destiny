package uk.co.alexlevy;

import org.apache.http.impl.client.HttpClients;
import uk.co.alexlevy.auth.AuthenticationService;
import uk.co.alexlevy.auth.PsnAuthenticationService;
import uk.co.alexlevy.cli.CommandLineInterface;
import uk.co.alexlevy.cli.Environment;
import uk.co.alexlevy.cli.RunConfiguration;
import uk.co.alexlevy.entities.DestinyCharacter;
import uk.co.alexlevy.entities.Inventory;
import uk.co.alexlevy.entities.OptimisedInventory;
import uk.co.alexlevy.loader.DestinyLoader;
import uk.co.alexlevy.loader.DestinyLoaderImpl;
import uk.co.alexlevy.optimiser.LightLevelOptimiser;
import uk.co.alexlevy.optimiser.LightLevelOptimiserImpl;
import uk.co.alexlevy.printing.ConsolePrintingService;
import uk.co.alexlevy.printing.PrintingService;
import uk.co.alexlevy.service.ApacheDestinyService;
import uk.co.alexlevy.service.DestinyService;

import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {
        Environment environment = new Environment();
        CommandLineInterface cli = new CommandLineInterface(environment);
        RunConfiguration config = cli.parse(args);
        AuthenticationService authenticationService = new PsnAuthenticationService(config.psnId, config.psnPass);
        DestinyService destinyService = new ApacheDestinyService(authenticationService, HttpClients.createDefault());
        DestinyLoader loader = new DestinyLoaderImpl(destinyService);
        PrintingService printingService = new ConsolePrintingService();
        LightLevelOptimiser optimiser = new LightLevelOptimiserImpl();

        long membershipId = loader.getMembershipId(config.gamerTag);

        List<DestinyCharacter> characters = loader.getCharacters(membershipId);

        printingService.print("");

        for (DestinyCharacter character : characters) {
            Inventory inventory = loader.getInventory(membershipId, character.getId());
            OptimisedInventory solution = optimiser.optimise(inventory);
            printingService.print(character);
            printingService.print(solution);
        }
    }
}