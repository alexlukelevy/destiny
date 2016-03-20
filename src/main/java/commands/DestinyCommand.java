package commands;

import service.DestinyService;
import service.PrintingService;

public abstract class DestinyCommand {

    protected DestinyService destinyService;
    protected PrintingService printingService;

    public DestinyCommand(DestinyService destinyService, PrintingService printingService) {
        this.destinyService = destinyService;
        this.printingService = printingService;
    }

    public abstract void run(String username);

}