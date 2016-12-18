import auth.AuthenticationService;
import auth.PsnAuthenticationService;
import cli.CommandLineInterface;
import cli.Environment;
import cli.RunConfiguration;
import entities.Bucket;
import entities.DestinyCharacter;
import optimiser.LightLevelOptimiser;
import optimiser.LightLevelOptimiserImpl;
import org.apache.http.impl.client.HttpClients;
import printing.ConsolePrintingService;
import printing.PrintingService;
import service.ApacheDestinyService;
import loader.DestinyLoader;
import loader.DestinyLoaderImpl;
import service.DestinyService;

import java.util.HashMap;
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

        for(DestinyCharacter character : characters) {
            List<Bucket> buckets = loader.getInventory(membershipId, character.getCharacterId());
            HashMap<String, String> solution = optimiser.optimise(buckets);
            printingService.print("Character: " + character.getCharacterId());
            printingService.print(solution);
        }
    }
}