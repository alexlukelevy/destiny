package loader;

import entities.Bucket;
import entities.DestinyCharacter;

import java.io.IOException;
import java.util.List;

public interface DestinyLoader {

    long getMembershipId(String username) throws IOException;

    List<DestinyCharacter> getCharacters(long membershipId) throws IOException;

    List<Bucket> getInventory(long membershipId, long characterId) throws IOException;

}
