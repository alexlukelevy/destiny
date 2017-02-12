package printing;

import entities.DestinyCharacter;
import entities.OptimisedInventory;

public interface PrintingService {

    void print(String output);

    void print(DestinyCharacter character);

    void print(OptimisedInventory inventory);
}