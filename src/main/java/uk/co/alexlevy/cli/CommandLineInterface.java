package uk.co.alexlevy.cli;

public class CommandLineInterface {

    private Environment environment;

    public CommandLineInterface(Environment environment) {
        this.environment = environment;
    }

    public RunConfiguration parse(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException("Please supply your PSN gamertag and email");
        }

        String gamerTag = args[0];
        String psnId = args[1];
        String psnPass = null;

        while (psnPass == null) {
            psnPass = environment.readLine("Please enter your PSN password: ");
        }

        return new RunConfiguration(gamerTag, psnId, psnPass);
    }
}