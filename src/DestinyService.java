import java.io.IOException;

public interface DestinyService {

    String getAccountSummary(int membershipTypeId, long membershipId) throws IOException;

}