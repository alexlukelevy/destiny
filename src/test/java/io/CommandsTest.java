package io;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommandsTest {

    @Test
    public void shouldReturnTrueIfCommandExists(){
        // Given
        String value = "character";

        // When
        boolean contains = Commands.contains(value);

        // Then
        assertThat(contains, is(true));
    }

    @Test
    public void shouldReturnFalseIfCommandDoesNotExist(){
        // Given
        String value = "geeza";

        // When
        boolean contains = Commands.contains(value);

        // Then
        assertThat(contains, is(false));
    }


}