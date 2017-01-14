package loader;

import com.fasterxml.jackson.databind.JsonNode;
import entities.Bucket;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.DestinyService;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static util.TestUtils.asJsonNode;

@RunWith(MockitoJUnitRunner.class)
public class DestinyLoaderImplTest {

    @InjectMocks
    private DestinyLoaderImpl classUnderTest;

    @Mock
    private DestinyService destinyService;

    @Test
    public void shouldLoadTheMembershipIdFromJson() throws IOException {
        // Given
        int membershipTypeId = 2;
        String username = "username";
        JsonNode root = asJsonNode("{ \"membershipId\": 1 }");

        given(destinyService.getMembership(membershipTypeId, username)).willReturn(root);

        // When
        long membershipId = classUnderTest.getMembershipId(username);

        // Then
        assertThat(membershipId, equalTo(1L));
    }

    @Test
    public void shouldLoadTheInventoryFromJson() throws IOException {
        // Given
        int membershipTypeId = 2;
        long membershipId = 3;
        long characterId = 4;

        String inventory = IOUtils.toString(this.getClass().getResourceAsStream("/inventory.json"));
        JsonNode root = asJsonNode(inventory);
        given(destinyService.getInventory(membershipTypeId, membershipId, characterId)).willReturn(root);

        // When
        List<Bucket> buckets = classUnderTest.getInventory(membershipId, characterId);

        // Then
        assertThat(buckets, not(equalTo(null)));
    }
}