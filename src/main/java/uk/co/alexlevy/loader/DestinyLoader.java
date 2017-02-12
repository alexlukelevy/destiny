package uk.co.alexlevy.loader;

import uk.co.alexlevy.entities.DestinyCharacter;
import uk.co.alexlevy.entities.Inventory;

import java.io.IOException;
import java.util.List;

public interface DestinyLoader {

    long getMembershipId(String username) throws IOException;

    List<DestinyCharacter> getCharacters(long membershipId) throws IOException;

    Inventory getInventory(long membershipId, long characterId) throws IOException;

}
