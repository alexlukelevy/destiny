package service;

import java.io.IOException;
import java.util.List;

public interface DestinyLoader {
    List<Bucket> getInventory(long membershipId, long characterId) throws IOException;
}
