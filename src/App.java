import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        DestinyService destinyService = new ApacheDestinyService(
                "http://www.bungie.net/Platform/Destiny",
                System.getenv("DESTINY_API_KEY")
        );
       long membershipId = destinyService.getMembershipId(2, "jonnyweb"); //args[0]
       // long membershipId = destinyService.getMembershipId(2, "BeneficialBewick"); //args[0]
        System.out.println(destinyService.getCharacterSummary(2, membershipId));
    }
}