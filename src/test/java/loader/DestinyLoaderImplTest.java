package loader;

import com.fasterxml.jackson.databind.JsonNode;
import entities.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.DestinyService;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
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
    public void shouldLoadTheCharactersFromJson() throws IOException {
        // Given
        int membershipTypeId = 2;
        long membershipId = 3;

        String characterSummary = IOUtils.toString(this.getClass().getResourceAsStream("/account-summary.json"));
        JsonNode root = asJsonNode(characterSummary);

        given(destinyService.getAccountSummary(membershipTypeId, membershipId)).willReturn(root);

        // When
        List<DestinyCharacter> characters = classUnderTest.getCharacters(membershipId);

        // Then
        assertThat(characters.size(), equalTo(3));
        assertThat(characters.get(0), equalTo(new DestinyCharacter(123L, CharacterClass.Titan, 10)));
        assertThat(characters.get(1), equalTo(new DestinyCharacter(456L, CharacterClass.Hunter, 20)));
        assertThat(characters.get(2), equalTo(new DestinyCharacter(789L, CharacterClass.Warlock, 30)));
    }

    @Test
    public void shouldLoadTheInventoryFromJson() throws IOException {
        // Given
        int membershipTypeId = 2;
        long membershipId = 3;
        long characterId = 4;

        String inventoryJson = IOUtils.toString(this.getClass().getResourceAsStream("/inventory.json"));
        JsonNode root = asJsonNode(inventoryJson);
        given(destinyService.getInventory(membershipTypeId, membershipId, characterId)).willReturn(root);

        // When
        Inventory inventory = classUnderTest.getInventory(membershipId, characterId);

        // Then
        List<Bucket> weapons = inventory.getWeapons();
        List<Bucket> armour = inventory.getArmour();

        assertThat(weapons.size(), equalTo(1));
        assertThat(weapons.get(0).getName(), equalTo("Primary Weapons"));
        assertThat(weapons.get(0).getItems().size(), equalTo(1));
        assertThat(weapons.get(0).getItems().get(0), equalTo(new Item("Rifle", 320, ItemGrade.Exotic)));

        assertThat(armour.size(), equalTo(1));
        assertThat(armour.get(0).getName(), equalTo("Gauntlets"));
        assertThat(armour.get(0).getItems().size(), equalTo(1));
        assertThat(armour.get(0).getItems().get(0), equalTo(new Item("Grasps", 310, ItemGrade.Rare)));
    }
}