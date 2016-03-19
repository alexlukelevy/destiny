import java.io.IOException;

public interface DestinyService {

    String getAccountSummary(int membershipTypeId, long membershipId) throws IOException;

    long getMembershipId(int membershipTypeId, String username) throws IOException;

    String getCharacterSummary(int membershipTypeId, long membershipId) throws IOException;

    String getCharacterInventory(int membershipTypeId, long membershipId) throws IOException;

}