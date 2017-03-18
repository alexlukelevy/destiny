package uk.co.alexlevy.printing;

import uk.co.alexlevy.entities.DestinyCharacter;
import uk.co.alexlevy.entities.OptimisedInventory;

public interface PrintingService {

    void print(String output);

    void print(DestinyCharacter character);

    void print(OptimisedInventory inventory);
}