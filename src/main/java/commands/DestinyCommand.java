package commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import service.DestinyService;
import service.PrintingService;

import java.io.IOException;

public abstract class DestinyCommand {

    protected Options options;
    protected DestinyService destinyService;
    protected PrintingService printingService;

    public DestinyCommand(Options options, DestinyService destinyService, PrintingService printingService) {
        this.options = options;
        this.destinyService = destinyService;
        this.printingService = printingService;
    }

    public abstract void run(String[] args) throws IOException;

    protected CommandLine getCommandLine(String[] args) {
        try {
            return new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse command line arguments", e);
        }
    }
}