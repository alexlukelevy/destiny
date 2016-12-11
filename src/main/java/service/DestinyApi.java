package service;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface DestinyApi {
    JsonNode getInventory(long membershipId, long characterId) throws IOException;
}
