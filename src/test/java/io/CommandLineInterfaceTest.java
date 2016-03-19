package io;

import org.junit.Before;
import org.junit.Test;

public class CommandLineInterfaceTest {

    private CommandLineInterface cli;

    @Before
    public void setUp() {
        cli = new CommandLineInterface();
    }

    @Test
    public void shouldDelegateAppropriatelyWhenGivenCharacterCliArgument(){
        // Given
        String[] args = new String[] {"character"};

        // When
        cli.run(args);

        // Then
        // TODO: wait for character delegate
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenGivenAnUnsupportedCliArgument(){
        // Given
        String[] args = new String[] {"geeza"};

        // When
        cli.run(args);

        // Then an IllegalArgumentException is thrown
    }
}