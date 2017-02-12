package entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CharacterClassTest {

    @Test
    public void shouldFindCharacterClassFromId() {
        // Given
        int titanId = 0;
        int hunterId = 1;
        int warlockId = 2;

        // When
        // Then
        assertThat(CharacterClass.fromId(titanId), equalTo(CharacterClass.Titan));
        assertThat(CharacterClass.fromId(hunterId), equalTo(CharacterClass.Hunter));
        assertThat(CharacterClass.fromId(warlockId), equalTo(CharacterClass.Warlock));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfCharacterClassIdNotFound() {
        // Given
        int fakeId = 3;

        // When
        CharacterClass.fromId(fakeId);

        // Then IllegalArgumentException is thrown
    }
}
