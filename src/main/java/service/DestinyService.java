package service;

import java.io.IOException;

public interface DestinyService {

    String getAccountSummary(int membershipTypeId, long membershipId) throws IOException;

    long getMembershipId(int membershipTypeId, String username) throws IOException;

    long getCharacterId(int membershipTypeId, long membershipId, int classTypeId) throws IOException;

    String getCharacterSummary(int membershipTypeId, long membershipId, long characterId) throws IOException;

    String getCharacterInventory(int membershipTypeId, long membershipId, long characterId) throws IOException;
}