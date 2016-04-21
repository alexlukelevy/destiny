package commands.character;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.DestinyService;
import service.PrintingService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CharacterCommandTest {

    @Mock
    private DestinyService destinyService;

    @Mock
    private PrintingService printingService;

    private CharacterCommand characterCommand;

    @Before
    public void setUp() {
        characterCommand = new CharacterCommand(new CharacterOptions().getOptions(), destinyService, printingService);
    }

    @Test
    public void shouldPrintTheSummaryForTheChosenCharacter() throws Exception {
        // Given
        String username = "LethalLevy";
        int membershipTypeId = 2;
        int classTypeId = 1;
        long membershipId = 1L;
        long characterId = 2L;
        String summary = "{lightLevel: 320}";

        given(destinyService.getMembershipId(membershipTypeId, username)).willReturn(membershipId);
        given(destinyService.getCharacterId(membershipTypeId, membershipId, classTypeId)).willReturn(characterId);
        given(destinyService.getCharacterSummary(membershipTypeId, membershipId, characterId)).willReturn(summary);

        String command = "-username LethalLevy -class hunter";

        // When
        characterCommand.getCharacterSummary(command.split(" "));

        // Then
        verify(printingService).print(summary);
    }
}