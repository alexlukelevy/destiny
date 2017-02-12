package cli;

public class CommandLineInterface {

    private Environment environment;

    public CommandLineInterface(Environment environment) {
        this.environment = environment;
    }

    public RunConfiguration parse(String[] args) {

        if (args.length < 1) {
            throw new IllegalArgumentException("Please supply your PSN gamertag");
        }

        String gamerTag = args[0];
        String psnId = environment.getVariable("PSN_ID");
        String psnPass = environment.getVariable("PSN_PASS");

        while (psnId == null) {
            psnId = environment.readLine("Please enter your PSN ID");
        }

        while (psnPass == null) {
            psnPass = environment.readLine("Please enter your PSN password");
        }

        return new RunConfiguration(gamerTag, psnId, psnPass);
    }
}