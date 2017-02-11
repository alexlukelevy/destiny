import auth.AuthenticationService;
import auth.PsnAuthenticationService;
import cli.CommandLineInterface;
import cli.Environment;
import cli.RunConfiguration;
import entities.DestinyCharacter;
import entities.Inventory;
import entities.OptimisedInventory;
import loader.DestinyLoader;
import loader.DestinyLoaderImpl;
import optimiser.LightLevelOptimiser;
import optimiser.LightLevelOptimiserImpl;
import org.apache.http.impl.client.HttpClients;
import printing.ConsolePrintingService;
import printing.PrintingService;
import service.ApacheDestinyService;
import service.DestinyService;

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

        for (DestinyCharacter character : characters) {
            Inventory inventory = loader.getInventory(membershipId, character.getId());
            OptimisedInventory solution = optimiser.optimise(inventory);
            printingService.print("Character: " + character.getCharacterClass() + " - " + character.getLevel());
            printingService.print(solution);
        }
    }
}