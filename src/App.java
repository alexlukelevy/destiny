import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        DestinyService destinyService = new ApacheDestinyService(
                "http://www.bungie.net/Platform/Destiny",
                "MY-API-KEY"
        );
        System.out.println(destinyService.getAccountSummary(2, 4611686018437660735L));
    }

}