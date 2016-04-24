import com.fasterxml.jackson.databind.JsonNode;

package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface DestinyService {

    String getAccountSummary(int membershipTypeId, long membershipId) throws IOException;

    long getMembershipId(int membershipTypeId, String username) throws IOException;


    String getCharacterSummary(int membershipTypeId, long membershipId, long characterId) throws IOException;

    long getCharacterId(int membershipTypeId, long membershipId, int classTypeId) throws IOException;
    
    List<JsonNode> getCharacterInventoryItems(int membershipTypeId, long membershipId) throws IOException;

    ArrayList<String> getCharacterWeapons(int membershipTypeId, long membershipId) throws IOException;

    ArrayList<String> getCharacterArmor(int membershipTypeId, long membershipId) throws IOException;

    String getCharacterInventory(int membershipTypeId, long membershipId, long characterId) throws IOException;
}