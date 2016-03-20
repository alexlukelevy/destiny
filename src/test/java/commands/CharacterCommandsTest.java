package commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.DestinyService;
import service.PrintingService;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CharacterCommandsTest {

    @Mock
    private DestinyService destinyService;

    @Mock
    private PrintingService printingService;

    @InjectMocks
    private CharacterCommand characterCommands;

    @Test
    public void shouldPrintTheSummaryForAllCharacters() throws IOException {
        // Given
        String username = "LethalLevy";
        int membershipTypeId = 2;
        long membershipId = 1L;
        String summary = "{lightLevel: 320}";

        given(destinyService.getMembershipId(membershipTypeId, username)).willReturn(membershipId);
        given(destinyService.getCharacterSummary(membershipTypeId, membershipId)).willReturn(summary);

        // When
        characterCommands.run(username);

        // Then
        verify(printingService).print(summary);
    }

}